package com.example.oportunia.domain.model

data class CompanyPublicationInput(
    val file: String,
    val paid: Boolean,
    val idLocation: Int,
    val idArea: Int,
    val idCompany: Int
)