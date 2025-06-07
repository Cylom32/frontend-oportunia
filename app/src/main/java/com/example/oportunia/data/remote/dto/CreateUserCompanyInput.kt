package com.example.oportunia.data.remote.dto

data class CreateUserCompanyInput(
    val email: String,
    val password: String,
    val img: String,
    val creationDate: String,
    val idRole: Int
)