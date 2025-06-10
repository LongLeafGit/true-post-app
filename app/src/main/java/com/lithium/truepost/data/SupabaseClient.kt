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

    private const val BASE_URL = "https://tjroybkgjemnbuqrxgja.supabase.co"
    private const val API_KEY =
        "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InRqcm95YmtnamVtbmJ1cXJ4Z2phIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDk0ODQwNDcsImV4cCI6MjA2NTA2MDA0N30.X4vkAC9QCMZ96yx0MHrEjPxH13p4haYbVkHfe4ILdPM"

    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json { prettyPrint = true; isLenient = true })
        }
    }

    suspend fun signUp(nombre: String, apellido: String, correo: String, contrasena: String): Result {
        val response: HttpResponse = client.post("$BASE_URL/auth/v1/signup") {
            contentType(ContentType.Application.Json)
            headers { append("apikey", API_KEY) }
            setBody(SignUpRequest(correo, contrasena, UserData(nombre, apellido))) // Enviar datos
        }
        return response.body<Result>()
    }

    suspend fun signIn(correo: String, contrasena: String): String {
        val response: HttpResponse = client.post("$BASE_URL/auth/v1/token?grant_type=password") {
            contentType(ContentType.Application.Json)
            headers { append("apikey", API_KEY) }
            setBody(SignInRequest(correo, contrasena))
        }
        val loginResponse = response.body<LoginResponse>()
        return loginResponse.access_token  // Devuelve el token de autenticación
    }

    suspend fun mandarDatosPruebas(
        idUsuario: Int, puntuacion: Int, tiempo: Int, errores: Int, aciertos: Int, tiempo2: Int
    ) {
        client.post("$BASE_URL/rest/v1/Investigacion") {
            headers {
                append("apikey", API_KEY)
                append("Authorization", "Bearer $API_KEY") // Supabase requiere autenticación
                append(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            }
            setBody(InvestigacionData(idUsuario, puntuacion, tiempo, errores, aciertos, tiempo2))
        }
    }


}

// Clases de solicitud y respuesta para el registro
@Serializable
data class SignUpRequest(
    val email: String,
    val password: String,
    val data: UserData // Agregar nombre y apellido en "data"
)

@Serializable
data class UserData(
    val nombre: String,
    val apellido: String
)


@Serializable
data class Result(
    val id: String,
    val email: String,
)

@Serializable
data class SignInRequest(
    val email: String,
    val password: String
)

@Serializable
data class LoginResponse(
    val access_token: String
)


@Serializable
data class InvestigacionData(
    val id_usuario: Int,
    val puntuacion: Int,
    val tiempo: Int,
    val errores: Int,
    val aciertos: Int,
    val tiempo2: Int
)