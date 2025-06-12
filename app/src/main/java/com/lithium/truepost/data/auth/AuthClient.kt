package com.lithium.truepost.data.auth

import com.lithium.truepost.BuildConfig
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.GoTrue
import io.github.jan.supabase.gotrue.gotrue
import io.github.jan.supabase.gotrue.providers.builtin.Email
import io.github.jan.supabase.gotrue.user.UserInfo
import io.github.jan.supabase.postgrest.Postgrest

object AuthClient {
    internal val client = createSupabaseClient(
        supabaseUrl = BuildConfig.SUPABASE_URL,
        supabaseKey = BuildConfig.SUPABASE_KEY,
    ) {
        install(GoTrue)
        install(Postgrest)
    }

    suspend fun signUp(email: String, password: String): ResultWrapper {
        return try {
            val result = client.gotrue.signUpWith(Email) {
                this.email = email
                this.password = password
            }
            ResultWrapper.Success(result)
        } catch (e: Exception) {
            ResultWrapper.Error(e.message ?: "Error desconocido")
        }
    }

    suspend fun signIn(email: String, password: String): ResultWrapper {
        return try {
            client.gotrue.loginWith(Email) {
                this.email = email
                this.password = password
            }
            val user: UserInfo? = client.gotrue.currentUserOrNull()
            val accessToken: String? = client.gotrue.currentAccessTokenOrNull()
            ResultWrapper.Success(user, accessToken)
        } catch (e: Exception) {
            ResultWrapper.Error(e.message ?: "Error desconocido")
        }
    }

    suspend fun logout() {
        client.gotrue.logout()
    }

    fun getCurrentUser(): UserInfo? = client.gotrue.currentUserOrNull()
    fun getAccessToken(): String? = client.gotrue.currentAccessTokenOrNull()
}

sealed class ResultWrapper {
    data class Success(val user: Any? = null, val token: String? = null): ResultWrapper()
    data class Error(val message: String): ResultWrapper()
}
