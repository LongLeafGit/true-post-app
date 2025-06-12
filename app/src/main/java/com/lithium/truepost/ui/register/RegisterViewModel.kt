package com.lithium.truepost.ui.register

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lithium.truepost.TruePostApplication
import com.lithium.truepost.data.auth.ResultWrapper
import com.lithium.truepost.data.local.UserEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class RegisterUiState(
    val firstname: String = "",
    val email: String = "",
    val password: String = "",
    val confirmP: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val registrationSuccess: Boolean = false,
    val registeredEmail: String? = null,
)

class RegisterViewModel(
    private val app: TruePostApplication,
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState = _uiState.asStateFlow()

    fun onStateChange(
        firstname: String? = null,
        email: String? = null,
        password: String? = null,
        confirmP: String? = null,
    ) {
        val current = _uiState.value
        _uiState.value = current.copy(
            firstname = firstname ?: current.firstname,
            email = email ?: current.email,
            password = password ?: current.password,
            confirmP = confirmP ?: current.confirmP,
        )
    }

    fun isFirstnameValid() = _uiState.value.firstname.isNotBlank()
    fun isEmailValid() = Patterns.EMAIL_ADDRESS.matcher(_uiState.value.email).matches()
    fun isPasswordValid(): Boolean {
        val password = _uiState.value.password
        val lengthOk = password.length >= 8
        val hasLowercase = password.any { it.isLowerCase() }
        val hasUppercase = password.any { it.isUpperCase() }
        val hasDigit = password.any { it.isDigit() }
        val hasSymbol = password.any { !it.isLetterOrDigit() }
        return lengthOk && hasLowercase && hasUppercase && hasDigit && hasSymbol
    }
    fun isConfirmPValid(): Boolean {
        val confirmP = _uiState.value.confirmP
        val password = _uiState.value.password
        return isPasswordValid() && confirmP == password
    }

    fun register() {
        val state = _uiState.value
        _uiState.value = state.copy(isLoading = true, error = null, registrationSuccess = false)
        viewModelScope.launch {
            try {
                val result = app.auth.signUp(state.email, state.password)
                if (result is ResultWrapper.Success) {
                    // Puedes acceder a info del registro con result.data (ajusta según tu wrapper)
                    app.database.userDao().insert(
                        UserEntity(
                            email = state.email,
                            firstname = state.firstname,
                            bestScore = 0,
                        )
                    )
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        registrationSuccess = true,
                        registeredEmail = state.email,
                        error = null
                    )
                } else if (result is ResultWrapper.Error) {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = result.message,
                        registrationSuccess = false
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(isLoading = false, error = e.message, registrationSuccess = false)
            }
        }
    }
}