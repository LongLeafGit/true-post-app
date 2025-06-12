package com.lithium.truepost.ui.quiz.component

import androidx.compose.foundation.layout.*
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
fun PauseMenu(
    onUnpauseClick: () -> Unit,
    onExitClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = "Pausa",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
        )
        Spacer(modifier = Modifier.height(64.dp))
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(onClick = onUnpauseClick, modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Continuar",
                    style = MaterialTheme.typography.titleLarge,
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = onExitClick, modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Salir",
                    style = MaterialTheme.typography.titleLarge,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PauseMenuPreview() {
    TruePostTheme {
        PauseMenu(
            onUnpauseClick = {},
            onExitClick = {},
            modifier = Modifier.fillMaxSize().padding(16.dp)
        )
    }
}
