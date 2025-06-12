package com.lithium.truepost.ui.quiz.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lithium.truepost.ui.theme.TruePostTheme

@Composable
fun QuizHeader(
    remainingTime: Int,
    maxTime: Int,
    onPauseClick: () -> Unit,
    onHintClick: () -> Unit,
    pauseDisabled: Boolean,
    hintDisabled: Boolean,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth()
    ) {
        OutlinedButton(
            onClick = onPauseClick,
            enabled = !pauseDisabled,
            border = BorderStroke(
                width = 2.dp,
                color = if (!hintDisabled) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary,
            )
        ) {
            Icon(
                imageVector = Icons.Filled.Pause,
                contentDescription = "Pausa",
                modifier = Modifier.size(32.dp)
            )
        }

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.size(64.dp)
        ) {
            CircularProgressIndicator(
                progress = { remainingTime / maxTime.toFloat() },
                modifier = Modifier.fillMaxSize(),
                strokeWidth = 4.dp,
            )
            Text(
                text = remainingTime.toString().padStart(2, '0'),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        }

        OutlinedButton(
            onClick = onHintClick,
            enabled = !hintDisabled,
            border = BorderStroke(
                width = 2.dp,
                color = if (!hintDisabled) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary,
            )
        ) {
            Icon(
                imageVector = Icons.Filled.Lightbulb,
                contentDescription = "Ayuda",
                modifier = Modifier.size(32.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun QuizHeaderPreview_Enabled() {
    TruePostTheme {
        QuizHeader(
            remainingTime = 8,
            maxTime = 10,
            onPauseClick = {},
            onHintClick = {},
            pauseDisabled = false,
            hintDisabled = false
        )
    }
}

@Preview(showBackground = true)
@Composable
fun QuizHeaderPreview_HintDisabled() {
    TruePostTheme {
        QuizHeader(
            remainingTime = 3,
            maxTime = 10,
            onPauseClick = {},
            onHintClick = {},
            pauseDisabled = false,
            hintDisabled = true
        )
    }
}

@Preview(showBackground = true)
@Composable
fun QuizHeaderPreview_BothDisabled() {
    TruePostTheme {
        QuizHeader(
            remainingTime = 0,
            maxTime = 10,
            onPauseClick = {},
            onHintClick = {},
            pauseDisabled = true,
            hintDisabled = true
        )
    }
}
