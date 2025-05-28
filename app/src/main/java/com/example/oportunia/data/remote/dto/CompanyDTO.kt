package com.example.oportunia.data.remote.dto

data class CompanyDTO(
    val idCompany: Int? = null,
    val companyName: String,
    val companyDescription: String? = null,
    val idUser: Int
)
