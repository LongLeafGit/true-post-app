package com.lithium.truepost.ui.menu.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lithium.truepost.R
import com.lithium.truepost.ui.theme.TruePostTheme

data class TempQuiz(
    @DrawableRes val quizResId: Int,
    val title: String,
    val noQuestions: Int = 10,
)

@Composable
fun QuizSelector(
    quiz: TempQuiz,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    ElevatedCard(
        onClick = onClick,
        modifier = modifier,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp),
        ) {
            Image(
                painter = painterResource(quiz.quizResId),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(150.dp),
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = quiz.title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = "${quiz.noQuestions} preguntas",
                style = MaterialTheme.typography.titleMedium,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun QuizSelectorPreview() {
    TruePostTheme {
        QuizSelector(
            quiz = TempQuiz(
                quizResId = R.drawable.facebook,
                title = "Facebook",
                noQuestions = 10,
            ),
            onClick = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
fun QuizSelectorPreviewTwo() {
    TruePostTheme {
        QuizSelector(
            quiz = TempQuiz(
                quizResId = R.drawable.x,
                title = "X (Twitter)",
                noQuestions = 10,
            ),
            onClick = {},
        )
    }
}