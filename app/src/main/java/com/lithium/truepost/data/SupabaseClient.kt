package com.lithium.truepost.data

import io.ktor.client.*
import io.ktor.client.call.body
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.HttpResponse
import io.ktor.http.*
import kotlinx.serialization.Serializable
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

object SupabaseClient {

    private const val BASE_URL = "https://<your-project-id>.supabase.co/"
    private const val API_KEY = "<your-supabase-api-key>"

    val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json { prettyPrint = true; isLenient = true })
        }
    }

    // Función para registrar un nuevo usuario en Supabase
    suspend fun signUp(email: String, password: String): Result {
        val response: HttpResponse = client.post("$BASE_URL/auth/v1/signup") {
            contentType(ContentType.Application.Json)
            headers {
                append("apikey", API_KEY)
            }
            setBody(SignUpRequest(email, password))
        }

        val signUpResponse = response.body<SignUpResponse>()

        return signUpResponse.result
    }
}

// Clases de solicitud y respuesta para el registro
@Serializable
data class SignUpRequest(
    val email: String,
    val password: String
)

@Serializable
data class SignUpResponse(
    val result: Result
)

@Serializable
data class Result(
    val id: String,
    val email: String
)
