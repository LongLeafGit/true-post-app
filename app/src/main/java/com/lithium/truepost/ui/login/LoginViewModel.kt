package com.lithium.truepost.ui.login

import androidx.lifecycle.ViewModel
import com.lithium.truepost.data.SupabaseClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch


data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val token: String? = null,
)

class LoginViewModel : ViewModel() {
    val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    fun setEmail(email: String) {
        _uiState.value = _uiState.value.copy(
            email = email
        )
    }

    fun setPassword(password: String) {
        _uiState.value = _uiState.value.copy(
            password = password
        )
    }

    fun isValidPassword(): Boolean {
        return _uiState.value.password.length >= 8
    }

    fun isValidEmail(): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(_uiState.value.email).matches()
    }

    fun login(): Boolean {
        var success = false
        viewModelScope.launch {
            try {
                val token = SupabaseClient.signIn(uiState.value.email, uiState.value.password)
                _uiState.value = _uiState.value.copy(token = token)
                success = true // Login exitoso
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(token = "Error: ${e.message}")
                success = false // Fallo en login
            }
        }
        return success
    }
}
