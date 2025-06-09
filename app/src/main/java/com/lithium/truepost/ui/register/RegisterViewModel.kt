package com.lithium.truepost.ui.register

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

data class RegisterUiState(
    val firstname: String = "",
    val lastname: String = "",
    val email: String = "",
    val password: String = "",
    val confirmP: String = "",
)

class RegisterViewModel : ViewModel() {
    val _uiState = MutableStateFlow(RegisterUiState())
    val uiState = _uiState.asStateFlow()

    fun onStateChange(
        firstname: String? = null,
        lastname: String? = null,
        email: String? = null,
        password: String? = null,
        confirmP: String? = null,
    ) {
        val current = _uiState.value
        _uiState.value = current.copy(
            firstname = firstname ?: current.firstname,
            lastname = lastname ?: current.lastname,
            email = email ?: current.email,
            password = password ?: current.password,
            confirmP = confirmP ?: current.confirmP,
        )
    }

    fun isFirstnameValid(): Boolean {
        return !_uiState.value.firstname.isBlank()
    }

    fun isLastnameValid(): Boolean {
        return !_uiState.value.lastname.isBlank()
    }

    fun isEmailValid(): Boolean {
        return !android.util.Patterns.EMAIL_ADDRESS.matcher(_uiState.value.email).matches()
    }

    fun isPasswordValid(): Boolean {
        return _uiState.value.password.length >= 8
    }

    fun isConfirmPValid(): Boolean {
        return isPasswordValid() && _uiState.value.confirmP == _uiState.value.password
    }

    fun register(): Boolean {
        // post on Supabase
        return true
    }
}
