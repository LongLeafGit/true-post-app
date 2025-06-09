package com.lithium.truepost.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lithium.truepost.R
import com.lithium.truepost.ui.theme.TruePostTheme

@Composable
fun HomeScreen(
    onRegisterClick: () -> Unit,
    onLoginClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.home),
            contentDescription = null,
            modifier = Modifier.fillMaxHeight(.4f).fillMaxWidth(),
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "¡Bienvenido a True Post!",
            style = MaterialTheme.typography.displayLarge,
            fontWeight = FontWeight.Black,
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Aprende a diferenciar publicaciones manipuladas por Facebook y Twitter",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(end = 32.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        ElevatedButton(
            onClick = onRegisterClick,
            colors = ButtonDefaults.buttonColors(
                contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
                containerColor = MaterialTheme.colorScheme.tertiaryContainer,
            )
        ) {
            Text(
                text = "Registrarse",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(16.dp).fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        ElevatedButton(
            onClick = onLoginClick,
            colors = ButtonDefaults.buttonColors(
                contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
            )
        ) {
            Text(
                text = "Iniciar Sesión",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(16.dp).fillMaxWidth()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    TruePostTheme {
        HomeScreen(
            onRegisterClick = {},
            onLoginClick = {},
        )
    }
}
