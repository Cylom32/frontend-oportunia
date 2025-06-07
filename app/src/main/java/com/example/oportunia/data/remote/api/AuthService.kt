package com.example.oportunia.data.remote.api

import com.example.oportunia.data.remote.dto.AuthRequestDto
import com.example.oportunia.data.remote.dto.AuthResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Retrofit service interface for authentication endpoints.
 */
interface AuthService {

    /**
     * POST https://your-base-url/v1/auth/login
     */
    @POST("v1/auth/login")
    suspend fun login(
        @Body request: AuthRequestDto
    ): Response<AuthResponseDto>

    /**
     * POST https://your-base-url/v1/auth/logout
     */
    @POST("v1/auth/logout")
    suspend fun logout(): Response<Unit>
}