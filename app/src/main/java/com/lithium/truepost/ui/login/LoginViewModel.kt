package com.lithium.truepost.ui.login

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

data class LoginUiState(
    val email: String = "",
    val password: String = "",
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
        // Send request to Supabase
        return true
    }
}