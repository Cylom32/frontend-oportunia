package com.example.oportunia.data.remote.dto

import java.time.LocalDate

data class StudentDTO(
    val idStudent: Int? = null,
    val idUser: Int? = null,
    val name: String?,
    val lastName1: String?,
    val lastName2: String?,
    val creationDate: LocalDate?,
    val idUniversity: Int?
)