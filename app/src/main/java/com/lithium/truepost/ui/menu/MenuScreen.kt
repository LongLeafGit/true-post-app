package com.lithium.truepost.ui.menu

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lithium.truepost.R
import com.lithium.truepost.ui.menu.component.CourseSelector
import com.lithium.truepost.ui.menu.component.QuizSelector
import com.lithium.truepost.ui.menu.component.TempCourse
import com.lithium.truepost.ui.menu.component.TempQuiz
import com.lithium.truepost.ui.theme.TruePostTheme

@Composable
fun MenuScreen(
    username: String,
    quizes: List<TempQuiz>,
    courses: List<TempCourse>,
    bestScore: Int,
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(
                text = "Hola $username",
                style = MaterialTheme.typography.titleSmall,
            )
            Text(
                text = "Mejor puntuación: $bestScore",
                style = MaterialTheme.typography.titleSmall,
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Cuestionarios",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
        )
        Spacer(modifier = Modifier.height(8.dp))
        LazyRow {
            items(quizes) { quiz ->
                QuizSelector(
                    quiz = quiz,
                    onClick = {},
                    modifier = Modifier.padding(end = 16.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Contenidos claves",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
        )
        Spacer(modifier = Modifier.height(8.dp))
        LazyColumn {
            items(courses) { course ->
                CourseSelector(
                    course = course,
                    onClick = {},
                    modifier = Modifier.padding(bottom = 16.dp),
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MenuScreenPreview() {
    TruePostTheme {
        MenuScreen(
            username = "Eduardo",
            bestScore = 100,
            quizes = listOf(
                TempQuiz(
                    quizResId = R.drawable.facebook,
                    title = "Facebook",
                ),
                TempQuiz(
                    quizResId = R.drawable.x,
                    title = "X (Twitter)",
                ),
            ),
            courses = listOf(
                TempCourse(
                    imageResId = R.drawable.bot,
                    title = "¿Qué son los bots sociales?",
                    description = "Aprende que son los bots y por son son sociales.",
                ),
                TempCourse(
                    imageResId = R.drawable.posts,
                    title = "Tipos de manipulación de información en redes sociales",
                    description = "Conoce las formas que existen de manipular la información.",
                ),
                TempCourse(
                    imageResId = R.drawable.fact_check,
                    title = "¿Cómo identificar publicaciones manipuladas y legítimas?",
                    description = "Entiende las diferencias entre publicaciones de redes sociales,",
                ),
                TempCourse(
                    imageResId = R.drawable.disinformation,
                    title = "El impacto de la desinformación en la sociedad digital",
                    description = "Comprende los efectos de la desinformación en tu vida diaria.",
                ),
            ),
        )
    }
}