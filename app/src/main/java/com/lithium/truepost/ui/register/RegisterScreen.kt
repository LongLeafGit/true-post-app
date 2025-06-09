package com.lithium.truepost.ui.register

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
    val lastnameError = !viewModel.isLastnameValid()
    val emailError = !viewModel.isEmailValid()
    val passwordError = !viewModel.isPasswordValid()
    val confirmError = !viewModel.isConfirmPValid()

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
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
                    onValueChange = { viewModel.onStateChange(firstname = it) },
                    label = "Nombre(s)",
                    isError = firstnameError,
                    errorMessage = "Campo requerido",
                    leadingIcon = Icons.Outlined.Person,
                    modifier = Modifier.fillMaxWidth()
                )

                PlainTextField(
                    value = uiState.lastname,
                    onValueChange = { viewModel.onStateChange(lastname = it) },
                    label = "Apellidos",
                    isError = lastnameError,
                    errorMessage = "Campo requerido",
                    leadingIcon = Icons.Outlined.Person,
                    modifier = Modifier.fillMaxWidth()
                )

                PlainTextField(
                    value = uiState.email,
                    onValueChange = { viewModel.onStateChange(email = it) },
                    label = "Correo electrónico",
                    isError = emailError,
                    errorMessage = "Correo inválido",
                    leadingIcon = Icons.Outlined.Email,
                    modifier = Modifier.fillMaxWidth()
                )

                SecretTextField(
                    secret = uiState.password,
                    onSecretChange = { viewModel.onStateChange(password = it) },
                    label = "Contraseña",
                    isError = passwordError,
                    errorMessage = "Debe tener al menos 8 caracteres",
                    modifier = Modifier.fillMaxWidth()
                )

                SecretTextField(
                    secret = uiState.confirmP,
                    onSecretChange = { viewModel.onStateChange(confirmP = it) },
                    label = "Confirmar contraseña",
                    isError = confirmError,
                    errorMessage = "Las contraseñas no coinciden",
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(24.dp))

                ElevatedButton(
                    onClick = {
                        focusManager.clearFocus()
                        if (!firstnameError && !lastnameError && !emailError && !passwordError && !confirmError) {
                            if (viewModel.register()) {
                                onRegisterSuccess()
                            }
                        }
                    },
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
fun RegisterScreenPreview() {
    MaterialTheme {
        RegisterScreen(
            onRegisterSuccess = {},
            onBackToLogin = {}
        )
    }
}
