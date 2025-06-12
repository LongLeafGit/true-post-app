package com.lithium.truepost.ui.register

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lithium.truepost.ui.TruePostViewModelProvider
import com.lithium.truepost.ui.common.PlainTextField
import com.lithium.truepost.ui.login.SecretTextField

@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel = viewModel(factory = TruePostViewModelProvider.Factory),
    onRegisterSuccess: () -> Unit,
    onBackToLogin: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val focusManager = LocalFocusManager.current

    val firstnameError = !viewModel.isFirstnameValid()
    val emailError = !viewModel.isEmailValid()
    val passwordError = !viewModel.isPasswordValid()
    val confirmError = !viewModel.isConfirmPValid()

    LaunchedEffect(uiState.registrationSuccess) {
        if (uiState.registrationSuccess) {
            onRegisterSuccess()
        }
    }

    RegisterScreenContent(
        uiState = uiState,
        firstnameError = firstnameError,
        emailError = emailError,
        passwordError = passwordError,
        confirmError = confirmError,
        onFirstnameChange = { viewModel.onStateChange(firstname = it) },
        onEmailChange = { viewModel.onStateChange(email = it) },
        onPasswordChange = { viewModel.onStateChange(password = it) },
        onConfirmChange = { viewModel.onStateChange(confirmP = it) },
        onRegister = {
            focusManager.clearFocus()
            if (!firstnameError && !emailError && !passwordError && !confirmError && !uiState.isLoading) {
                viewModel.register()
            }
        },
        onBackToLogin = onBackToLogin
    )
}

@Composable
fun RegisterScreenContent(
    uiState: RegisterUiState,
    firstnameError: Boolean,
    emailError: Boolean,
    passwordError: Boolean,
    confirmError: Boolean,
    onFirstnameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onConfirmChange: (String) -> Unit,
    onRegister: () -> Unit,
    onBackToLogin: () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())
    ) {
        ElevatedCard(modifier = Modifier.padding(16.dp)) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(32.dp)
            ) {
                Text(
                    text = "Crear cuenta",
                    style = MaterialTheme.typography.headlineLarge
                )

                Spacer(modifier = Modifier.height(16.dp))

                PlainTextField(
                    value = uiState.firstname,
                    onValueChange = onFirstnameChange,
                    label = "Nombre(s)",
                    isError = firstnameError,
                    errorMessage = "Campo requerido",
                    leadingIcon = Icons.Outlined.Person,
                    modifier = Modifier.fillMaxWidth()
                )

                PlainTextField(
                    value = uiState.email,
                    onValueChange = onEmailChange,
                    label = "Correo electrónico",
                    isError = emailError,
                    errorMessage = "Correo inválido",
                    leadingIcon = Icons.Outlined.Email,
                    modifier = Modifier.fillMaxWidth()
                )

                SecretTextField(
                    secret = uiState.password,
                    onSecretChange = onPasswordChange,
                    label = "Contraseña",
                    isError = passwordError,
                    errorMessage = "Debe tener al menos 8 caracteres, una minúscula, una mayúscula, un número y un símbolo",
                    modifier = Modifier.fillMaxWidth()
                )

                SecretTextField(
                    secret = uiState.confirmP,
                    onSecretChange = onConfirmChange,
                    label = "Confirmar contraseña",
                    isError = confirmError,
                    errorMessage = "Las contraseñas no coinciden",
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(24.dp))

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
                    onClick = onRegister,
                    enabled = !firstnameError && !emailError && !passwordError && !confirmError && !uiState.isLoading,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer,
                        contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                ) {
                    Text(
                        text = "Registrarse",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(4.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "¿Ya tienes una cuenta? Inicia sesión",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = MaterialTheme.colorScheme.primary,
                        textAlign = TextAlign.Center
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onBackToLogin() }
                        .padding(top = 8.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterScreenContentPreview() {
    MaterialTheme {
        RegisterScreenContent(
            uiState = RegisterUiState(
                firstname = "Ejemplo",
                email = "ejemplo@mail.com",
                password = "12345678",
                confirmP = "12345678",
                isLoading = false,
                error = null
            ),
            firstnameError = false,
            emailError = false,
            passwordError = false,
            confirmError = false,
            onFirstnameChange = {},
            onEmailChange = {},
            onPasswordChange = {},
            onConfirmChange = {},
            onRegister = {},
            onBackToLogin = {}
        )
    }
}