package com.example.oportunia.data.remote.dto

import java.time.LocalDate

data class UserWhitoutId(
    val email: String,
    val password: String,
    val img: String?,
    val creationDate: String,
    val idRole: Int
)
