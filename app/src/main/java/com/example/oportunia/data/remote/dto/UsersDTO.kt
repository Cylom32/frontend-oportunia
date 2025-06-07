package com.example.oportunia.data.remote.dto

import java.time.LocalDate

data class UsersDTO(
    val idUser: Int? = null,
    val email: String,
    val password: String,
    val img: String?,
 //   val creationDate: LocalDate? = LocalDate.now(),
    val creationDate: String,
    val idRole: Int?
)