package com.example.oportunia.domain.model

data class CompanyWithNetworks(
    val idCompany: Int,
    val companyName: String,
    val companyDescription: String?,
    val socialNetworks: List<SocialNetwork>
)