package com.example.oportunia.data.mapper

import com.example.oportunia.data.remote.dto.AuthRequestDto
import com.example.oportunia.data.remote.dto.AuthResponseDto
import com.example.oportunia.domain.model.AuthResult
import com.example.oportunia.domain.model.Credentials

/**
 * Mapper para convertir entre DTOs de red y modelos de dominio de autenticación.
 */
object AuthMapper {

    /**
     * Convierte un [Credentials] de dominio en un [AuthRequestDto] para la API.
     */
    fun credentialsToDto(credentials: Credentials): AuthRequestDto =
        AuthRequestDto(
            email    = credentials.username,
            password = credentials.password
        )

    /**
     * Convierte un [AuthResponseDto] de la API en un [AuthResult] de dominio.
     */
    fun dtoToAuthResult(dto: AuthResponseDto): AuthResult =
        AuthResult(
            token  = dto.token,
            userId = dto.userId
        )
}
