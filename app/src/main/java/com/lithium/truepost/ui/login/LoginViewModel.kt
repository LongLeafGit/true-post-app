package com.lithium.truepost.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lithium.truepost.TruePostApplication
import com.lithium.truepost.data.auth.ResultWrapper
import io.github.jan.supabase.gotrue.user.UserInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val user: UserInfo? = null,
    val accessToken: String? = null,
)

class LoginViewModel(
    private val app: TruePostApplication
) : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    fun setEmail(email: String) {
        _uiState.value = _uiState.value.copy(email = email)
    }

    fun setPassword(password: String) {
        _uiState.value = _uiState.value.copy(password = password)
    }

    fun isValidPassword(): Boolean {
        val password = _uiState.value.password
        val lengthOk = password.length >= 8
        val hasLowercase = password.any { it.isLowerCase() }
        val hasUppercase = password.any { it.isUpperCase() }
        val hasDigit = password.any { it.isDigit() }
        val hasSymbol = password.any { !it.isLetterOrDigit() }
        return lengthOk && hasLowercase && hasUppercase && hasDigit && hasSymbol
    }
    fun isValidEmail(): Boolean = android.util.Patterns.EMAIL_ADDRESS.matcher(_uiState.value.email).matches()

    fun login() {
        val state = _uiState.value
        _uiState.value = state.copy(isLoading = true, error = null)
        viewModelScope.launch {
            val result = app.auth.signIn(state.email, state.password)
            if (result is ResultWrapper.Success) {
                _uiState.value = _uiState.value.copy(
                    user = result.user as? UserInfo,
                    accessToken = result.token,
                    isLoading = false,
                    error = null
                )
            } else if (result is ResultWrapper.Error) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = result.message
                )
            }
        }
    }
}
