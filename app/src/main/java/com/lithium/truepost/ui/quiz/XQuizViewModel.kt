package com.lithium.truepost.ui.quiz

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lithium.truepost.TruePostApplication
import com.lithium.truepost.data.model.XTweetModel
import com.lithium.truepost.data.raw.AllXTweets
import com.lithium.truepost.data.quiz.QuizClient
import com.lithium.truepost.data.quiz.QuestionTelemetry
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.math.min

data class XQuizUiState(
    val maxQuestions: Int,
    val maxTime: Int = 10,
    val remainingTime: Int = maxTime,
    val tweets: List<XTweetModel> = emptyList(),
    val questionIndex: Int = 0,
    val points: Int = 0,
    val correct: Int = 0,
    val incorrect: Int = 0,
    val totalTime: Int = 0,
    val lastAnswerWasCorrect: Boolean = false,
    val quizFinished: Boolean = false,
    val pause: Boolean = false,
    val animating: Boolean = false,
    val questionsTelemetry: List<QuestionTelemetry> = emptyList(), // agrega si necesitas
    val finishDate: String? = null,
    val hintUsed: Boolean = false,
)

class XQuizViewModel(
    private val app: TruePostApplication,
    maxQuestions: Int,
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        XQuizUiState(
            maxQuestions = min(maxQuestions, AllXTweets.size),
            tweets = AllXTweets.shuffled().take(min(maxQuestions, AllXTweets.size)),
        )
    )
    val uiState = _uiState.asStateFlow()

    private var timerJob: Job? = null
    private var questionStartTime: Long = System.currentTimeMillis()

    init {
        startTimer()
    }

    private fun startTimer() {
        timerJob?.cancel()
        questionStartTime = System.currentTimeMillis()
        timerJob = viewModelScope.launch {
            while (_uiState.value.remainingTime > 0 && !_uiState.value.pause && !_uiState.value.quizFinished) {
                delay(1000)
                _uiState.value = _uiState.value.copy(
                    remainingTime = _uiState.value.remainingTime - 1,
                    totalTime = _uiState.value.totalTime + 1
                )
            }
            if (!_uiState.value.pause && !_uiState.value.quizFinished) {
                onTimeExpired()
            }
        }
    }

    private fun stopTimer() {
        timerJob?.cancel()
        timerJob = null
    }

    fun onPauseClick() {
        stopTimer()
        _uiState.value = _uiState.value.copy(pause = true)
    }

    fun onUnpauseClick() {
        _uiState.value = _uiState.value.copy(pause = false)
        startTimer()
    }

    fun onHintClick() {
        val state = _uiState.value
        // Solo si no se ha usado, ni terminado, ni está animando
        if (state.hintUsed || state.quizFinished || state.animating) return

        _uiState.value = _uiState.value.copy(
            hintUsed = true
        )
        stopTimer() // Detenemos el tiempo si estaba corriendo

        val currentPost = state.tweets[state.questionIndex]
        val pointsEarned = 10 + state.remainingTime
        val nextIndex = state.questionIndex + 1
        val quizFinished = nextIndex >= state.tweets.size

        val responseTime = ((System.currentTimeMillis() - questionStartTime) / 1000).toInt().coerceAtLeast(1)
        val questionTelemetry = QuestionTelemetry(
            postId = currentPost.id,
            userAnsweredLegit = currentPost.isLegit, // El hint da la respuesta correcta
            wasCorrect = true,
            responseTime = responseTime,
            timestamp = currentIsoTimestamp()
        )
        val newTelemetry = state.questionsTelemetry + questionTelemetry

        _uiState.value = _uiState.value.copy(
            correct = state.correct + 1,
            points = state.points + pointsEarned,
            lastAnswerWasCorrect = true,
            animating = true,
            questionsTelemetry = newTelemetry
        )

        viewModelScope.launch {
            delay(3000)
            _uiState.value = _uiState.value.copy(
                questionIndex = if (quizFinished) state.questionIndex else nextIndex,
                quizFinished = quizFinished,
                remainingTime = state.maxTime,
                animating = false,
                finishDate = if (quizFinished) currentIsoTimestamp() else null
            )
            if (quizFinished) {
                sendResultsToSupabase()
            } else {
                startTimer()
            }
        }
    }

    fun onResponseClick(userResponseIsLegit: Boolean) {
        if (_uiState.value.quizFinished || _uiState.value.animating) return
        stopTimer()
        val state = _uiState.value
        val currentPost = state.tweets[state.questionIndex]
        val isCorrect = currentPost.isLegit == userResponseIsLegit
        val pointsEarned = if (isCorrect) 10 + state.remainingTime else 0
        val nextIndex = state.questionIndex + 1
        val quizFinished = nextIndex >= state.tweets.size

        // Si quieres guardar telemetría, puedes implementarlo aquí:
        val responseTime = ((System.currentTimeMillis() - questionStartTime) / 1000).toInt().coerceAtLeast(1)
        val questionTelemetry = QuestionTelemetry(
            postId = currentPost.id,
            userAnsweredLegit = userResponseIsLegit,
            wasCorrect = isCorrect,
            responseTime = responseTime,
            timestamp = currentIsoTimestamp()
        )
        val newTelemetry = state.questionsTelemetry + questionTelemetry

        _uiState.value = state.copy(
            correct = if (isCorrect) state.correct + 1 else state.correct,
            incorrect = if (!isCorrect) state.incorrect + 1 else state.incorrect,
            points = state.points + pointsEarned,
            lastAnswerWasCorrect = isCorrect,
            animating = true,
            questionsTelemetry = newTelemetry
        )

        viewModelScope.launch {
            delay(3000)
            _uiState.value = _uiState.value.copy(
                questionIndex = if (quizFinished) state.questionIndex else nextIndex,
                quizFinished = quizFinished,
                remainingTime = state.maxTime,
                animating = false,
                finishDate = if (quizFinished) currentIsoTimestamp() else null
            )
            if (quizFinished) {
                sendResultsToSupabase()
            } else {
                startTimer()
            }
        }
    }

    private fun onTimeExpired() {
        val state = _uiState.value
        val nextIndex = state.questionIndex + 1
        val quizFinished = nextIndex >= state.tweets.size

        // Si quieres guardar telemetría de timeout, hazlo aquí:
        val responseTime = ((System.currentTimeMillis() - questionStartTime) / 1000).toInt().coerceAtLeast(1)
        val currentPost = state.tweets[state.questionIndex]
        val questionTelemetry = QuestionTelemetry(
            postId = currentPost.id,
            userAnsweredLegit = false,
            wasCorrect = false,
            responseTime = responseTime,
            timestamp = currentIsoTimestamp()
        )
        val newTelemetry = state.questionsTelemetry + questionTelemetry

        _uiState.value = state.copy(
            incorrect = state.incorrect + 1,
            lastAnswerWasCorrect = false,
            animating = true,
            questionsTelemetry = newTelemetry
        )

        viewModelScope.launch {
            delay(3000)
            _uiState.value = _uiState.value.copy(
                questionIndex = if (quizFinished) state.questionIndex else nextIndex,
                quizFinished = quizFinished,
                remainingTime = state.maxTime,
                animating = false,
                finishDate = if (quizFinished) currentIsoTimestamp() else null
            )
            if (quizFinished) {
                sendResultsToSupabase()
            } else {
                startTimer()
            }
        }
    }

    fun sendResultsToSupabase() {
        val state = _uiState.value
        val userId = app.auth.getCurrentUser()?.id
        val email = app.auth.getCurrentUser()?.email
        val accessToken = app.auth.getAccessToken()

        println("AUTH: userId = $userId")
        println("AUTH: email = $email")
        println("AUTH: accessToken = $accessToken")

        if (userId == null || email == null) {
            println("AUTH: No hubo sesión activa")
            return
        }

        viewModelScope.launch {
            val user = app.database.userDao().getByEmail(email)
            val userName = user?.firstname ?: ""

            println("AUTH: Enviando resultados del quiz. userName=$userName")

            QuizClient.sendQuizResult(
                userId = userId,
                username = userName,
                points = state.points,
                correct = state.correct,
                incorrect = state.incorrect,
                totalTime = state.totalTime,
                quizType = "x (twitter)",
                finishedAt = state.finishDate ?: currentIsoTimestamp(),
                questionsTelemetry = state.questionsTelemetry
            )

            // Actualizar bestScore si se superó
            if (user != null && state.points > user.bestScore) {
                val updatedUser = user.copy(bestScore = state.points)
                app.database.userDao().insert(updatedUser)
                println("Nuevo récord personal guardado: ${updatedUser.bestScore}")
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        stopTimer()
    }
}
