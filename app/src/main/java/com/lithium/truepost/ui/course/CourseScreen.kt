package com.lithium.truepost.ui.course

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lithium.truepost.data.model.CourseContent
import com.lithium.truepost.ui.TruePostViewModelProvider
import com.lithium.truepost.ui.theme.TruePostTheme

@Composable
fun CourseScreen(
    courseId: Int,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    viewModel: CourseViewModel = viewModel(factory = TruePostViewModelProvider.Factory),
) {
    LaunchedEffect(courseId) {
        viewModel.loadCourse(courseId)
    }
    val uiState by viewModel.uiState.collectAsState()
    CourseScreenContent(
        uiState = uiState,
        modifier = modifier,
        onBackClick = onBackClick,
        onMarkCompleted = { viewModel.markCompleted() }
    )
}

@Composable
fun CourseScreenContent(
    uiState: CourseUiData,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    onMarkCompleted: () -> Unit,
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
        // Header: Back + title
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(onClick = onBackClick) {
                androidx.compose.material3.Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Regresar"
                )
            }
            Text(
                text = uiState.title,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))

        // Description
        Text(
            text = uiState.description,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(start = 8.dp, end = 8.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Course content blocks
        uiState.content.forEachIndexed { idx, block ->
            CourseContentBlock(
                content = block,
                rightAligned = idx % 2 == 1
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Mark as completed if not already
        if (!uiState.completed) {
            Button(
                onClick = onMarkCompleted,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("Marcar como completado")
            }
        } else {
            Text(
                text = "¡Curso completado!",
                style = MaterialTheme.typography.titleMedium.copy(
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(16.dp)
            )
        }
    }
}

@Composable
private fun CourseContentBlock(
    content: CourseContent,
    rightAligned: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .shadow(1.dp, MaterialTheme.shapes.medium)
            .padding(6.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (content.imageResId != null) {
            if (rightAligned) {
                CourseBlockText(content, Modifier.weight(2f))
                CourseBlockImage(content, Modifier.weight(1f))
            } else {
                CourseBlockImage(content, Modifier.weight(1f))
                CourseBlockText(content, Modifier.weight(2f))
            }
        } else {
            CourseBlockText(content, Modifier.fillMaxWidth())
        }
    }
}

@Composable
private fun CourseBlockImage(content: CourseContent, modifier: Modifier = Modifier) {
    content.imageResId?.let {
        Image(
            painter = painterResource(it),
            contentDescription = null,
            modifier = modifier
                .fillMaxWidth()
                .height(80.dp)
        )
    }
}

@Composable
private fun CourseBlockText(content: CourseContent, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(
            text = content.title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = content.text,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Justify
        )
    }
}

// ----------- Preview -----------

@Preview(showBackground = true, widthDp = 400)
@Composable
fun CourseScreenPreview() {
    // MOCK DATA
    val mockContents = listOf(
        CourseContent(
            title = "¿Qué son bots sociales?",
            imageResId = com.lithium.truepost.R.drawable.disinformation, // Usa tus propios drawables
            text = "Un bot es un programa automatizado que simula ser una persona en redes sociales."
        ),
        CourseContent(
            title = "¿Cómo se comporta un bot?",
            imageResId = com.lithium.truepost.R.drawable.posts,
            text = "No tiene emociones reales, solo repite lo que le programaron. A veces comete errores raros o responde cosas sin sentido."
        ),
        CourseContent(
            title = "Cómo identificar un bot",
            imageResId = com.lithium.truepost.R.drawable.fact_check,
            text = "Usuario extraño o genérico, publica a todas horas, repite frases, solo habla de un tema con intensidad exagerada."
        ),
        CourseContent(
            title = "¿Para qué se usan los bots?",
            imageResId = com.lithium.truepost.R.drawable.bot,
            text = "Para hacer propaganda política o comercial, confundir o dividir, inflar la fama artificialmente."
        )
    )
    val uiState = CourseUiData(
        id = 1,
        imageResId = com.lithium.truepost.R.drawable.facebook,
        title = "Bots sociales en redes",
        description = "Aprende cómo funcionan y cómo identificar bots sociales.",
        completed = false,
        content = mockContents
    )
    TruePostTheme {
        Surface(color = Color.White) {
            CourseScreenContent(
                uiState = uiState,
                onBackClick = {},
                onMarkCompleted = {}
            )
        }
    }
}