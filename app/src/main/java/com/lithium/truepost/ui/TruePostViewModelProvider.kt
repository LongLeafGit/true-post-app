package com.lithium.truepost.ui

import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.lithium.truepost.ui.login.LoginViewModel
import com.lithium.truepost.ui.register.RegisterViewModel

object TruePostViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            LoginViewModel()
        }
        initializer {
            RegisterViewModel()
        }
    }
}