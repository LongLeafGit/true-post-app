package com.lithium.truepost.ui.quiz.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lithium.truepost.ui.theme.TruePostTheme

@Composable
fun FinalResult(
    correct: Int,
    incorrect: Int,
    points: Int,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Text(
            text = "¡Prueba Finalizada!",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text("Correctas: $correct", style = MaterialTheme.typography.titleLarge)
        Text("Incorrectas: $incorrect", style = MaterialTheme.typography.titleLarge)
        Text("Puntos: $points", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = onBackClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Volver al menú",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FinalResultPreview() {
    TruePostTheme {
        FinalResult(
            correct = 7,
            incorrect = 3,
            points = 108,
            onBackClick = {},
        )
    }
}