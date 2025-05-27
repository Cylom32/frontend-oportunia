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

    /**
     * Authenticates a user with provided [credentials].
     * Returns [AuthResult] on success or an exception on failure.
     */
    suspend fun login(credentials: Credentials): Result<AuthResult> =
        withContext(Dispatchers.IO) {
            try {
                // Convert credentials and log the request payload
                val requestDto = AuthMapper.credentialsToDto(credentials)
                Log.d(
                    "AuthRemoteDataSource",
                    "Login request payload: username=${requestDto.email}, password length=${requestDto.password.length}"
                )

                // Perform the network call
                val response: Response<AuthResponseDto> = authService.login(requestDto)

                if (response.isSuccessful) {
                    // First try Authorization header
                    val authHeader = response.headers()["Authorization"]
                    val body = response.body()
                    if (!authHeader.isNullOrBlank() && body !=null) {
                        Log.d(
                            "AuthRemoteDataSource",
                            "Found token in Authorization header: ${authHeader.take(15)}..."
                        )
                        return@withContext Result.success(
                            AuthResult(
                                token = authHeader,
                                email = body.email // placeholder or parse from header if available
                            )
                        )
                    }

                    // Fallback to response body  -->Esto no se al rato esta de mas
                    if (body != null) {
                        Log.d("AuthRemoteDataSource", "Login successful with body: $body")
                        return@withContext Result.success(AuthMapper.dtoToAuthResult(body))
                    }

                    // Fallback to response body
                    response.body()?.let { dto ->
                        Log.d("AuthRemoteDataSource", "Login successful with body: $dto")
                        return@withContext Result.success(AuthMapper.dtoToAuthResult(dto))
                    }
                    Log.e("AuthRemoteDataSource", "Login response body was null")
                    Result.failure(Exception("No token found in response"))
                } else {
                    // Handle HTTP error codes
                    val errorBody = response.errorBody()?.string()
                    val errorMsg = when (response.code()) {
                        401 -> "Unauthorized access"
                        403 -> "Invalid credentials"
                        else -> "API error ${response.code()}: $errorBody"
                    }
                    Log.e("AuthRemoteDataSource", "Login failed: $errorMsg")
                    Result.failure(Exception(errorMsg))
                }
            } catch (e: Exception) {
                Log.e("AuthRemoteDataSource", "Login exception: ${e.message}", e)
                Result.failure(e)
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
