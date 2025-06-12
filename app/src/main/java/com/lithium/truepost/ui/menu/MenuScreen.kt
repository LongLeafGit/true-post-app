package com.lithium.truepost.ui.menu

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lithium.truepost.R
import com.lithium.truepost.data.model.CourseData
import com.lithium.truepost.ui.TruePostViewModelProvider
import com.lithium.truepost.ui.menu.component.CourseSelector
import com.lithium.truepost.ui.menu.component.QuizSelector
import com.lithium.truepost.ui.menu.component.TempQuiz
import com.lithium.truepost.ui.session.SessionViewModel
import com.lithium.truepost.ui.theme.TruePostTheme

@Composable
fun MenuScreen(
    onFacebookQuizClick: () -> Unit,
    onXQuizClick: () -> Unit,
    onCourseClick: (Int) -> Unit,
    onExitClick: () -> Unit,
    viewModel: MenuViewModel = viewModel(factory = TruePostViewModelProvider.Factory),
    sessionViewModel: SessionViewModel = viewModel(factory = TruePostViewModelProvider.Factory),
) {
    val uiState by viewModel.uiState.collectAsState()

    MenuScreenContent(
        name = uiState.name,
        bestScore = uiState.bestScore,
        courses = uiState.courses,
        onFacebookQuizClick = onFacebookQuizClick,
        onXQuizClick = onXQuizClick,
        onCourseClick = onCourseClick,
        exitSession = { sessionViewModel.logout(); onExitClick() }
    )
}

@Composable
private fun MenuScreenContent(
    name: String,
    bestScore: Int,
    courses: List<CourseData>,
    onFacebookQuizClick: () -> Unit,
    onXQuizClick: () -> Unit,
    exitSession: () -> Unit,
    onCourseClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize().padding(16.dp),
        contentPadding = PaddingValues(bottom = 32.dp)
    ) {
        item {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = "Hola $name",
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
            Row {
                QuizSelector(
                    quiz = TempQuiz(
                        quizResId = R.drawable.facebook,
                        title = "Facebook",
                    ),
                    onClick = onFacebookQuizClick,
                    modifier = Modifier.padding(end = 16.dp)
                )
                QuizSelector(
                    quiz = TempQuiz(
                        quizResId = R.drawable.x,
                        title = "X (Twitter)",
                    ),
                    onClick = onXQuizClick,
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Contenidos claves",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
        items(courses) { course ->
            CourseSelector(
                course = course,
                onClick = { onCourseClick(course.id) },
                modifier = Modifier.padding(bottom = 16.dp),
            )
        }
        item {
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = exitSession,
                modifier = Modifier.fillMaxWidth().padding(16.dp),
            ) {
                Text(
                    text = "Cerrar sesión",
                    style = MaterialTheme.typography.titleMedium,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MenuScreenPreview() {
    val mockCourses = listOf(
        CourseData(
            id = 1,
            imageResId = R.drawable.facebook,
            title = "Curso de Facebook",
            description = "Aprende a identificar publicaciones falsas en Facebook.",
            completed = true,
            content = emptyList()
        ),
        CourseData(
            id = 2,
            imageResId = R.drawable.x,
            title = "Curso de X (Twitter)",
            description = "Aprende a identificar fake news en X.",
            completed = false,
            content = emptyList()
        )
    )

    TruePostTheme {
        MenuScreenContent(
            name = "Usuario Demo",
            bestScore = 12,
            courses = mockCourses,
            onFacebookQuizClick = {},
            onXQuizClick = {},
            onCourseClick = {},
            exitSession = {},
        )
    }
}
