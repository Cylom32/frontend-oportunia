package com.example.oportunia.domain.model

data class CreateCompanyInput(
    val companyName: String,
    val companyDescription: String,
    val idUser: Int
)