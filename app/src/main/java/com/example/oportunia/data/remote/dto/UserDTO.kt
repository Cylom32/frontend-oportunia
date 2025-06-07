package com.example.oportunia.data.remote.dto

data class UserDTO(
    val idUser: Int,
    val email: String,
    val password: String,
    val img: String,
    val creationDate: String,
    val role: RoleDTO
)