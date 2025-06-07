package com.example.oportunia.domain.model

data class UserResponseCompany(
    val idUser: Int,
    val email: String,
    val password: String,
    val img: String,
    val creationDate: String,
    val role: Role
)