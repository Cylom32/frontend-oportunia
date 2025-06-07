package com.example.oportunia.domain.model

data class CreateCompanyResponse(
    val idCompany: Int,
    val companyName: String,
    val companyDescription: String,
    val user: CompanyUserDTO
)