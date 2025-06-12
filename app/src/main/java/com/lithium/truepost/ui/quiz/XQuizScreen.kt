package com.lithium.truepost.ui.quiz

import com.lithium.truepost.ui.quiz.component.FinalResult
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lithium.truepost.data.raw.AllXTweets
import com.lithium.truepost.ui.TruePostViewModelProvider
import com.lithium.truepost.ui.quiz.component.PauseMenu
import com.lithium.truepost.ui.quiz.component.QuizHeader
import com.lithium.truepost.ui.quiz.component.QuizInteraction
import com.lithium.truepost.ui.quiz.component.XTweet
import com.lithium.truepost.ui.theme.TruePostTheme

@Composable
fun XQuizScreen(
    viewModel: XQuizViewModel = viewModel(factory = TruePostViewModelProvider.Factory),
    onExitClick: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()
    XQuizScreenContent(
        uiState = uiState,
        onPauseClick = { viewModel.onPauseClick() },
        onUnpauseClick = { viewModel.onUnpauseClick() },
        onHintClick = { viewModel.onHintClick() },
        onResponseClick = { viewModel.onResponseClick(it) },
        onExitClick = onExitClick,
    )
}

@Composable
private fun XQuizScreenContent(
    uiState: XQuizUiState,
    onPauseClick: () -> Unit,
    onUnpauseClick: () -> Unit,
    onHintClick: () -> Unit,
    onExitClick: () -> Unit,
    onResponseClick: (Boolean) -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize().padding(16.dp),
    ) {
        QuizHeader(
            remainingTime = uiState.remainingTime,
            maxTime = uiState.maxTime,
            onPauseClick = { onPauseClick() },
            onHintClick = if (uiState.hintUsed) ({}) else onHintClick,
            pauseDisabled = uiState.pause || uiState.quizFinished,
            hintDisabled = uiState.pause || uiState.quizFinished || uiState.hintUsed,
        )
        Spacer(modifier = Modifier.height(16.dp))
        Spacer(modifier = Modifier.weight(1f))

        when {
            uiState.pause -> {
                PauseMenu(
                    onUnpauseClick = onUnpauseClick,
                    onExitClick = onExitClick,
                )
            }
            uiState.quizFinished -> {
                FinalResult(
                    correct = uiState.correct,
                    incorrect = uiState.incorrect,
                    points = uiState.points,
                    onBackClick = onExitClick,
                )
            }
            else -> {
                XTweet(
                    uiState.tweets[uiState.questionIndex],
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .heightIn(max = 450.dp)
                        .fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Pregunta ${uiState.questionIndex + 1} de ${uiState.maxQuestions}",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.weight(1f))
                QuizInteraction(
                    lastCorrect = uiState.lastAnswerWasCorrect,
                    animating = uiState.animating,
                    onResponseClick = onResponseClick
                )
            }
        }
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Preview(showBackground = true)
@Composable
fun XQuizScreenPreview() {
    val uiState = XQuizUiState(
        tweets = AllXTweets,
        maxQuestions = 10,
    )
    TruePostTheme {
        XQuizScreenContent(
            uiState = uiState,
            onPauseClick = {},
            onUnpauseClick = {},
            onHintClick = {},
            onResponseClick = {},
            onExitClick = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
fun XQuizScreenPausePreview() {
    val uiState = XQuizUiState(
        tweets = AllXTweets,
        maxQuestions = 10,
        pause = true,
    )
    TruePostTheme {
        XQuizScreenContent(
            uiState = uiState,
            onPauseClick = {},
            onUnpauseClick = {},
            onHintClick = {},
            onResponseClick = {},
            onExitClick = {},
        )
    }
}
