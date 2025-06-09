package com.lithium.truepost.ui.login

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.outlined.Password
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.lithium.truepost.ui.theme.TruePostTheme

@Composable
fun SecretTextField(
    secret: String,
    onSecretChange: (String) -> Unit,
    label: String,
    placeholder: String = "Escribe aquí",
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    errorMessage: String = "",
    leadingIcon: ImageVector? = Icons.Outlined.Password,
    textStyle: TextStyle = MaterialTheme.typography.titleMedium
) {
    var isVisible by rememberSaveable { mutableStateOf(false) }
    var hasInteracted by rememberSaveable { mutableStateOf(false) }

    OutlinedTextField(
        value = secret,
        onValueChange = {
            if (!hasInteracted) hasInteracted = true
            onSecretChange(it)
        },
        label = { Text(label) },
        placeholder = { Text(placeholder) },
        singleLine = true,
        isError = hasInteracted && isError,
        modifier = modifier,
        visualTransformation = if (isVisible) VisualTransformation.None else PasswordVisualTransformation(),
        textStyle = textStyle,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        leadingIcon = {
            leadingIcon?.let {
                Icon(imageVector = it, contentDescription = null)
            }
        },
        trailingIcon = {
            val icon = if (isVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
            val description = if (isVisible) "Ocultar" else "Mostrar"
            IconButton(onClick = { isVisible = !isVisible }) {
                Icon(imageVector = icon, contentDescription = description)
            }
        },
        supportingText = {
            if (hasInteracted && isError && errorMessage.isNotBlank()) {
                Text(text = errorMessage, color = MaterialTheme.colorScheme.error)
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun SecretTextFieldPreview_Empty() {
    var password by rememberSaveable { mutableStateOf("") }
    TruePostTheme {
        SecretTextField(
            secret = password,
            onSecretChange = { password = it },
            label = "Contraseña",
            isError = password.length < 8,
            errorMessage = "La contraseña debe tener al menos 8 carácteres",
            modifier = Modifier.fillMaxWidth()
        )
    }
}
