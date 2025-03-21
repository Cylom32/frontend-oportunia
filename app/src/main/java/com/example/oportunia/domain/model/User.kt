package com.example.oportunia.domain.model

import java.util.Date


/**
 * Represents a user in the system.
 * @property id The unique identifier of the user.
 * @property email The email of the user.
 * @property password The password of the user.
 * @property img The profile image of the user.
 * @property creationDate The date when the user was created.
 * @property roleId The role associated with the user.
 */
data class User(
    val id: Int,
    val email: String,
    val password: String,
    val img: String?,
    val creationDate: Date,
    val roleId: Int?
)