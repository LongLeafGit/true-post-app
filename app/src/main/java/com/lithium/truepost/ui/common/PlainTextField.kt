package com.lithium.truepost.ui.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.TextFields
import androidx.compose.material3.Icon
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
import androidx.compose.ui.tooling.preview.Preview
import com.lithium.truepost.ui.theme.TruePostTheme

@Composable
fun PlainTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String = "Escribe aquí",
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    errorMessage: String = "",
    leadingIcon: ImageVector? = Icons.Outlined.TextFields,
    textStyle: TextStyle = MaterialTheme.typography.titleMedium
) {
    var hasInteracted by rememberSaveable { mutableStateOf(false) }

    OutlinedTextField(
        value = value,
        onValueChange = {
            if (!hasInteracted) hasInteracted = true
            onValueChange(it)
        },
        label = { Text(label) },
        placeholder = { Text(placeholder) },
        singleLine = true,
        isError = hasInteracted && isError,
        modifier = modifier,
        textStyle = textStyle,
        leadingIcon = {
            leadingIcon?.let {
                Icon(imageVector = it, contentDescription = null)
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
fun PlainTextFieldPreview() {
    var value by rememberSaveable { mutableStateOf("") }
    TruePostTheme {
        PlainTextField(
            value = value,
            onValueChange = { value = it },
            label = "Nombre(s)",
            isError = value.isBlank(),
            errorMessage = "Este campo no puede estar vacío",
            modifier = Modifier.fillMaxWidth()
        )
    }
}
