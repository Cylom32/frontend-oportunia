package com.example.oportunia.data.remote

import android.util.Log
import com.example.oportunia.data.mapper.AuthMapper
import com.example.oportunia.data.remote.api.AuthService
import com.example.oportunia.data.remote.dto.AuthRequestDto
import com.example.oportunia.data.remote.dto.AuthResponseDto
import com.example.oportunia.domain.model.AuthResult
import com.example.oportunia.domain.model.Credentials
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

/**
 * Remote data source for authentication operations.
 * Handles network calls using [AuthService] and maps DTOs to domain models via [AuthMapper].
 */
class AuthRemoteDataSource @Inject constructor(
    private val authService: AuthService
) {

    /** Guarda el último token obtenido tras el login */
    var lastAuthToken: String? = null
        private set

    /**
     * Hace login con correo y password, extrae y almacena el token del header "Authorization"
     * y devuelve un AuthResult con token y email.
     */
    suspend fun login(credentials: Credentials): Result<AuthResult> =
        withContext(Dispatchers.IO) {
            try {
                // Construir DTO de petición
                val requestDto: AuthRequestDto = AuthMapper.credentialsToDto(credentials)
                // Llamada al servicio
                val response: Response<AuthResponseDto> = authService.login(requestDto)

                if (response.isSuccessful) {
                    val authHeader: String? = response.headers()["Authorization"]
                    val body: AuthResponseDto? = response.body()

                    if (!authHeader.isNullOrBlank() && body != null) {
                        // Log y almacenamiento del token
                        Log.d(
                            "AuthRemoteDataSource",
                            "Found token in Authorization header: ${authHeader.take(15)}..."
                        )
                        lastAuthToken = authHeader

                        // Devolver el resultado con token y email
                        return@withContext Result.success(
                            AuthResult(
                                token = authHeader,
                                email = body.email
                            )
                        )
                    } else {
                        return@withContext Result.failure(
                            Exception("Authorization header o body nulo")
                        )
                    }
                } else {
                    // Manejo de errores HTTP
                    val errorBody: String? = response.errorBody()?.string()
                    val msg = when (response.code()) {
                        401 -> "Unauthorized access"
                        403 -> "Invalid credentials"
                        else -> "API error ${response.code()}: $errorBody"
                    }
                    return@withContext Result.failure(Exception(msg))
                }
            } catch (e: Exception) {
                // Errores de red u otros
                return@withContext Result.failure(e)
            }
        }

    /**
     * Logs out the current user.
     * If a logout endpoint exists in [AuthService], it will be called; otherwise it returns success.
     */
    suspend fun logout(): Result<Unit> =
        safeApiCall {
            authService.logout()
        }

    /**
     * Helper to execute API calls safely and wrap results in [Result].
     */
    private suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): Result<T> =
        withContext(Dispatchers.IO) {
            try {
                val response = apiCall()
                if (response.isSuccessful) {
                    response.body()?.let {
                        Result.success(it)
                    } ?: Result.failure(Exception("Response body was null"))
                } else {
                    val errorBody = response.errorBody()?.string()
                    val msg = when (response.code()) {
                        401 -> "Unauthorized access"
                        403 -> "Invalid credentials"
                        else -> "API error ${response.code()}: $errorBody"
                    }
                    Result.failure(Exception(msg))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
}
