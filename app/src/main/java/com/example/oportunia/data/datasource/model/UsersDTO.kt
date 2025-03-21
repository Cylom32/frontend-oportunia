package com.example.oportunia.data.datasource.model

import java.time.LocalDate

data class UsersDTO(
    val idUser: Int? = null,
    val email: String,
    val password: String,
    val img: String?,
    val creationDate: LocalDate? = LocalDate.now(),
    val idRole: Int?
)