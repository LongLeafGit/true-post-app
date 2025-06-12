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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.lithium.truepost.ui.session.SessionViewModel

@Composable
fun LoginScreen(
    sessionViewModel: SessionViewModel = viewModel(factory = TruePostViewModelProvider.Factory),
    viewModel: LoginViewModel = viewModel(factory = TruePostViewModelProvider.Factory),
    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()

    val emailError = !viewModel.isValidEmail()
    val passwordError = !viewModel.isValidPassword()

    LaunchedEffect(uiState.user, uiState.accessToken) {
        if (uiState.user != null && uiState.accessToken != null) {
            sessionViewModel.loadCurrentSession()
            onLoginClick()
        }
    }

    LoginScreenContent(
        uiState = uiState,
        emailError = emailError,
        passwordError = passwordError,
        onEmailChange = viewModel::setEmail,
        onPasswordChange = viewModel::setPassword,
        onLogin = { viewModel.login() },
        onRegisterClick = onRegisterClick,
    )
}

@Composable
private fun LoginScreenContent(
    uiState: LoginUiState,
    emailError: Boolean,
    passwordError: Boolean,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLogin: () -> Unit,
    onRegisterClick: () -> Unit,
) {
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        ElevatedCard(modifier = Modifier.padding(16.dp)) {
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(32.dp)) {
                Text(
                    text = "Iniciar Sesión",
                    style = MaterialTheme.typography.headlineLarge,
                )

                Spacer(modifier = Modifier.height(16.dp))

                PlainTextField(
                    label = "Correo",
                    value = uiState.email,
                    onValueChange = onEmailChange,
                    isError = emailError,
                    errorMessage = "Correo inválido",
                    leadingIcon = Icons.Outlined.Email,
                    modifier = Modifier.fillMaxWidth(),
                )

                SecretTextField(
                    label = "Contraseña",
                    secret = uiState.password,
                    onSecretChange = onPasswordChange,
                    isError = passwordError,
                    errorMessage = "Debe tener al menos 8 caracteres, una minúscula, una mayúscula, un número y un símbolo",
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                if (uiState.isLoading) {
                    CircularProgressIndicator(modifier = Modifier.padding(8.dp))
                }

                uiState.error?.let {
                    Text(
                        text = it,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }

                ElevatedButton(
                    onClick = {
                        if (!emailError && !passwordError && !uiState.isLoading) {
                            onLogin()
                        }
                    },
                    enabled = !emailError && !passwordError && !uiState.isLoading,
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
        LoginScreenContent(
            uiState = LoginUiState(
                email = "ejemplo@mail.com",
                password = "12345678",
                isLoading = false,
                error = null
            ),
            emailError = false,
            passwordError = false,
            onEmailChange = {},
            onPasswordChange = {},
            onLogin = {},
            onRegisterClick = {}
        )
    }
}
