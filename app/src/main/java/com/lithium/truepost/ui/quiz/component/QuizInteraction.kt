package com.lithium.truepost.ui.quiz.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.StopCircle
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lithium.truepost.ui.theme.TruePostTheme

@Composable
fun QuizInteraction(
    lastCorrect: Boolean,
    animating: Boolean,
    onResponseClick: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        if (animating) {
            ElevatedButton(
                onClick = {},
                enabled = false,
                colors = ButtonColors(
                    containerColor = if (lastCorrect) Color.Green else Color.Red,
                    contentColor = Color.White,
                    disabledContainerColor = if (lastCorrect) Color.Green else Color.Red,
                    disabledContentColor = Color.White,
                ),
                elevation = ButtonDefaults.elevatedButtonElevation(defaultElevation = 4.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = if (lastCorrect) Icons.Filled.CheckCircle else Icons.Filled.StopCircle,
                    contentDescription = if (lastCorrect) "Respuesta correcta" else "Respuesta incorrecta",
                    tint = Color.White,
                    modifier = Modifier.size(48.dp),
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = if (lastCorrect) "Respuesta correcta" else "Respuesta incorrecta",
                    color = Color.White,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                )
                Spacer(modifier = Modifier.weight(1.5f))
            }
        } else {
            Text(
                text = "Selecciona la categoría a la que pertenece la publicación",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineMedium,
            )
            Spacer(modifier = Modifier.height(16.dp))
            ElevatedButton(
                onClick = { onResponseClick(true) },
                colors = ButtonDefaults.elevatedButtonColors(
                    containerColor = Color.Green,
                    contentColor = Color.White,
                ),
                elevation = ButtonDefaults.elevatedButtonElevation(defaultElevation = 4.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Publicación Legítima",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(8.dp)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            ElevatedButton(
                onClick = { onResponseClick(false) },
                colors = ButtonDefaults.elevatedButtonColors(
                    containerColor = Color.Red,
                    contentColor = Color.White,
                ),
                elevation = ButtonDefaults.elevatedButtonElevation(defaultElevation = 4.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Publicación Ilegítima",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun QuizInteractionPreview() {
    TruePostTheme {
        QuizInteraction(
            lastCorrect = true,
            animating = false,
            onResponseClick = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
fun QuizInteractionPreviewAnim() {
    TruePostTheme {
        QuizInteraction(
            lastCorrect = true,
            animating = true,
            onResponseClick = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
fun QuizInteractionPreviewAnimFailed() {
    TruePostTheme {
        QuizInteraction(
            lastCorrect = false,
            animating = true,
            onResponseClick = {},
        )
    }
}

