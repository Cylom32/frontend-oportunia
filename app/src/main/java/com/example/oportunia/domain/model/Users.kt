package com.example.oportunia.domain.model

import java.time.LocalDate

data class Users(
    val id: Int,
    val email: String,
    val password: String,
    val img: String?,
    val creationDate: LocalDate,
    val roleId: Int?
)
