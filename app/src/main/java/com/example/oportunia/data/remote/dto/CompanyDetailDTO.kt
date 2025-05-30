package com.example.oportunia.data.remote.dto

data class CompanyDetailDTO(
    val idCompany: Int,
    val companyName: String,
    val companyDescription: String,
    val user: UserDTO
)