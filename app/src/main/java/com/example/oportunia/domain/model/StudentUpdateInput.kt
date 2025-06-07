package com.example.oportunia.domain.model

data class StudentUpdateInput(
    val firstName: String,
    val lastName1: String,
    val lastName2: String,
    val idUniversity: Int,
    val idUser: Int
)