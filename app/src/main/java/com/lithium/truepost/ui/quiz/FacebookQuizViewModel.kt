package com.lithium.truepost.ui.quiz

import android.os.Build
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lithium.truepost.TruePostApplication
import com.lithium.truepost.data.auth.AuthClient.client
import com.lithium.truepost.data.model.FacebookPostModel
import com.lithium.truepost.data.quiz.QuestionTelemetry
import com.lithium.truepost.data.raw.AllFacebookPosts
import com.lithium.truepost.data.quiz.QuizClient
import io.github.jan.supabase.gotrue.gotrue
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

data class QuizUiState(
    val maxQuestions: Int,
    val maxTime: Int = 10,
    val remainingTime: Int = maxTime,
    val posts: List<FacebookPostModel> = emptyList(),
    val questionIndex: Int = 0,
    val points: Int = 0,
    val correct: Int = 0,
    val incorrect: Int = 0,
    val totalTime: Int = 0,
    val lastAnswerWasCorrect: Boolean = false,
    val quizFinished: Boolean = false,
    val finishDate: String? = null,
    val pause: Boolean = false,
    val questionsTelemetry: List<QuestionTelemetry> = emptyList(),
    val animating: Boolean = false,
    val hintUsed: Boolean = false,
)

class FacebookQuizViewModel(
    private val app: TruePostApplication,
    maxQuestions: Int
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        QuizUiState(
            maxQuestions = minOf(maxQuestions, AllFacebookPosts.size),
            posts = AllFacebookPosts.shuffled().take(minOf(maxQuestions, AllFacebookPosts.size))
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
        // Solo si no se ha usado el hint antes y no está animando ni terminado el quiz
        val state = _uiState.value
        if (state.hintUsed || state.quizFinished || state.animating) return

        _uiState.value = _uiState.value.copy(
            hintUsed = true
        )
        stopTimer() // Detener el timer si estaba corriendo

        val currentPost = state.posts[state.questionIndex]
        val pointsEarned = 10 + state.remainingTime
        val nextIndex = state.questionIndex + 1
        val quizFinished = nextIndex >= state.posts.size

        val responseTime = ((System.currentTimeMillis() - questionStartTime) / 1000).toInt().coerceAtLeast(1)
        val questionTelemetry = QuestionTelemetry(
            postId = currentPost.id,
            userAnsweredLegit = currentPost.isLegit, // El hint siempre da la respuesta correcta
            wasCorrect = true,
            responseTime = responseTime,
            timestamp = currentIsoTimestamp()
        )
        val newTelemetry = state.questionsTelemetry + questionTelemetry

        // Mostrar animación de respuesta correcta
        _uiState.value = _uiState.value.copy(
            correct = state.correct + 1,
            points = state.points + pointsEarned,
            lastAnswerWasCorrect = true,
            animating = true,
        )

        viewModelScope.launch {
            delay(3000)
            // Siguiente pregunta o finalizar quiz
            _uiState.value = _uiState.value.copy(
                questionIndex = if (quizFinished) state.questionIndex else nextIndex,
                quizFinished = quizFinished,
                remainingTime = state.maxTime,
                finishDate = if (quizFinished) currentIsoTimestamp() else null,
                questionsTelemetry = newTelemetry,
                animating = false
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
        val currentPost = state.posts[state.questionIndex]
        val isCorrect = currentPost.isLegit == userResponseIsLegit
        val pointsEarned = if (isCorrect) 10 + state.remainingTime else 0
        val nextIndex = state.questionIndex + 1
        val quizFinished = nextIndex >= state.posts.size

        val responseTime = ((System.currentTimeMillis() - questionStartTime) / 1000).toInt().coerceAtLeast(1)
        val questionTelemetry = QuestionTelemetry(
            postId = currentPost.id,
            userAnsweredLegit = userResponseIsLegit,
            wasCorrect = isCorrect,
            responseTime = responseTime,
            timestamp = currentIsoTimestamp()
        )
        val newTelemetry = state.questionsTelemetry + questionTelemetry

        // 1. Mostrar animación de respuesta durante 3 segundos
        _uiState.value = state.copy(
            correct = if (isCorrect) state.correct + 1 else state.correct,
            incorrect = if (!isCorrect) state.incorrect + 1 else state.incorrect,
            points = state.points + pointsEarned,
            lastAnswerWasCorrect = isCorrect,
            animating = true,
            // el resto igual
        )

        viewModelScope.launch {
            delay(3000)
            // 2. Actualizar pregunta o terminar quiz
            _uiState.value = _uiState.value.copy(
                questionIndex = if (quizFinished) state.questionIndex else nextIndex,
                quizFinished = quizFinished,
                remainingTime = state.maxTime,
                finishDate = if (quizFinished) currentIsoTimestamp() else null,
                questionsTelemetry = newTelemetry,
                animating = false
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
        val currentPost = state.posts[state.questionIndex]
        val nextIndex = state.questionIndex + 1
        val quizFinished = nextIndex >= state.posts.size

        val responseTime = ((System.currentTimeMillis() - questionStartTime) / 1000).toInt().coerceAtLeast(1)
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
            questionIndex = if (quizFinished) state.questionIndex else nextIndex,
            quizFinished = quizFinished,
            remainingTime = state.maxTime,
            finishDate = if (quizFinished) currentIsoTimestamp() else null,
            questionsTelemetry = newTelemetry
        )

        if (quizFinished) {
            sendResultsToSupabase()
        } else {
            startTimer()
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
                quizType = "facebook",
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


fun currentIsoTimestamp(): String {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        java.time.Instant.now().toString()
    } else {
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)
        sdf.timeZone = TimeZone.getTimeZone("UTC")
        sdf.format(Date())
    }
}
