package com.lithium.truepost.ui.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lithium.truepost.ui.TruePostViewModelProvider
import com.lithium.truepost.ui.common.PlainTextField

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = viewModel(factory = TruePostViewModelProvider.Factory),
    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()

    val emailError = !viewModel.isValidEmail()
    val passwordError = !viewModel.isValidPassword()

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize(),
    ) {
        ElevatedCard(
            modifier = Modifier.padding(16.dp),
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(32.dp),
            ) {
                Text(
                    text = "Iniciar Sesión",
                    style = MaterialTheme.typography.headlineLarge,
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Correo
                PlainTextField(
                    label = "Correo",
                    value = uiState.email,
                    onValueChange = viewModel::setEmail,
                    isError = emailError,
                    errorMessage = "Correo inválido",
                    leadingIcon = Icons.Outlined.Email,
                    modifier = Modifier.fillMaxWidth(),
                )

                // Contraseña
                SecretTextField(
                    label = "Contraseña",
                    secret = uiState.password,
                    onSecretChange = viewModel::setPassword,
                    isError = passwordError,
                    errorMessage = "Debe tener al menos 8 caracteres",
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                ElevatedButton(
                    onClick = {
                        if (!emailError && !passwordError) {
                            if (viewModel.login()) {
                                onLoginClick()
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                        containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    )
                ) {
                    Text(
                        text = "Entrar",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(4.dp)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // LINK: ¿No tienes cuenta? Regístrate
                Text(
                    text = "¿No tienes cuenta? Regístrate",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = MaterialTheme.colorScheme.primary,
                        textAlign = TextAlign.Center
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onRegisterClick() }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    MaterialTheme {
        LoginScreen(
            onLoginClick = {},
            onRegisterClick = {}
        )
    }
}
