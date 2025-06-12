package com.lithium.truepost.ui.quiz

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import com.lithium.truepost.data.raw.AllFacebookPosts
import com.lithium.truepost.ui.TruePostViewModelProvider
import com.lithium.truepost.ui.quiz.component.FacebookPost
import com.lithium.truepost.ui.quiz.component.FinalResult
import com.lithium.truepost.ui.quiz.component.PauseMenu
import com.lithium.truepost.ui.quiz.component.QuizHeader
import com.lithium.truepost.ui.quiz.component.QuizInteraction
import com.lithium.truepost.ui.theme.TruePostTheme

@Composable
fun FacebookQuizScreen(
    viewModel: FacebookQuizViewModel = viewModel(factory = TruePostViewModelProvider.Factory),
    onBackToMenu: () -> Unit = {},
) {
    val uiState by viewModel.uiState.collectAsState()

    FacebookQuizScreenContent(
        uiState = uiState,
        onPauseClick = { viewModel.onPauseClick() },
        onUnpauseClick = { viewModel.onUnpauseClick() },
        onHintClick = { viewModel.onHintClick() },
        onResponseClick = { viewModel.onResponseClick(it) },
        onBackToMenu = onBackToMenu,
    )
}

@Composable
private fun FacebookQuizScreenContent(
    uiState: QuizUiState,
    onPauseClick: () -> Unit,
    onUnpauseClick: () -> Unit,
    onHintClick: () -> Unit,
    onResponseClick: (Boolean) -> Unit,
    onBackToMenu: () -> Unit,
) {
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        item {
            QuizHeader(
                remainingTime = uiState.remainingTime,
                maxTime = uiState.maxTime,
                onPauseClick = { onPauseClick() },
                onHintClick = if (uiState.hintUsed) ({}) else onHintClick,
                pauseDisabled = uiState.pause || uiState.quizFinished,
                hintDisabled = uiState.pause || uiState.quizFinished || uiState.hintUsed,
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            when {
                uiState.pause -> {
                    PauseMenu(
                        onUnpauseClick = onUnpauseClick,
                        onExitClick = onBackToMenu,
                        modifier = Modifier
                    )
                }
                uiState.quizFinished -> {
                    FinalResult(
                        correct = uiState.correct,
                        incorrect = uiState.incorrect,
                        points = uiState.points,
                        onBackClick = onBackToMenu,
                        modifier = Modifier
                    )
                }
                else -> {
                    FacebookPost(
                        post = uiState.posts[uiState.questionIndex],
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Pregunta ${uiState.questionIndex + 1} de ${uiState.maxQuestions}",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    QuizInteraction(
                        lastCorrect = uiState.lastAnswerWasCorrect,
                        animating = uiState.animating,
                        onResponseClick = onResponseClick
                    )
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FacebookQuizScreenPreview() {
    val uiState = QuizUiState(
        posts = AllFacebookPosts,
        maxQuestions = 10,
    )
    TruePostTheme {
        FacebookQuizScreenContent(
            uiState,
            onPauseClick = {},
            onUnpauseClick = {},
            onHintClick = {},
            onResponseClick = {},
            onBackToMenu = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
fun FacebookQuizScreenPausePreview() {
    val uiState = QuizUiState(
        posts = AllFacebookPosts,
        maxQuestions = 10,
        pause = true,
    )
    TruePostTheme {
        FacebookQuizScreenContent(
            uiState,
            onPauseClick = {},
            onUnpauseClick = {},
            onHintClick = {},
            onResponseClick = {},
            onBackToMenu = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
fun FacebookQuizScreenResultPreview() {
    val uiState = QuizUiState(
        posts = AllFacebookPosts,
        maxQuestions = 10,
        correct = 7,
        incorrect = 3,
        points = 140,
        quizFinished = true
    )
    TruePostTheme {
        FacebookQuizScreenContent(
            uiState,
            onPauseClick = {},
            onUnpauseClick = {},
            onHintClick = {},
            onResponseClick = {},
            onBackToMenu = {},
        )
    }
}
