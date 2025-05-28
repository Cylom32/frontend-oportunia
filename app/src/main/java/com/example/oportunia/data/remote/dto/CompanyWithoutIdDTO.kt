package com.example.oportunia.data.remote.dto

data class CompanyWithoutIdDTO(
    val companyName: String,
    val companyDescription: String? = null,
    val idUser: Int
)
