package com.example.oportunia.domain.model

import java.util.Date

/**
 * Represents a security user for authentication.
 * @property id The unique identifier of the security user.
 * @property tokenExpired The date when the token expires.
 * @property userId The user associated with the security record.
 */
data class SecurityUser(
    val id: Int,
    val tokenExpired: Date?,
    val userId: Int
)


