package com.lithium.truepost

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.lithium.truepost.data.TruePostDatabase
import com.lithium.truepost.data.auth.AuthClient

class TruePostApplication : Application() {
    lateinit var database: TruePostDatabase
        private set

    lateinit var auth: AuthClient
        private set

    override fun onCreate() {
        super.onCreate()
        database = TruePostDatabase.getDatabase(this)
        auth = AuthClient
    }
}

fun CreationExtras.truePostApplication(): TruePostApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as TruePostApplication)