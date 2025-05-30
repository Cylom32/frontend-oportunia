package com.example.oportunia.data.remote.dto

data class CompanyNestedDTO(
    val idCompany: Int,
    val companyName: String,
    val companyDescription: String,
    val user: UserDTO
)