package com.lithium.truepost.ui.session

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lithium.truepost.TruePostApplication
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class SessionData(
    val email: String,
    val name: String,
    val accessToken: String?
)

class SessionViewModel(
    private val app: TruePostApplication
) : ViewModel() {
    private val _session = MutableStateFlow<SessionData?>(null)
    val session = _session.asStateFlow()

    fun loadCurrentSession(onSessionActive: (() -> Unit)? = null) {
        viewModelScope.launch {
            val userInfo = app.auth.getCurrentUser()
            val accessToken = app.auth.getAccessToken()
            if (userInfo?.email != null) {
                val user = app.database.userDao().getByEmail(userInfo.email!!)
                _session.value = SessionData(
                    email = userInfo.email!!,
                    name = user?.firstname ?: "",
                    accessToken = accessToken
                )
                onSessionActive?.invoke()
            } else {
                _session.value = null
            }
        }
    }

    fun logout() {
        _session.value = null
        viewModelScope.launch { app.auth.logout() }
    }
}