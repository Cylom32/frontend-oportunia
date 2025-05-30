package com.example.oportunia.data.remote.dto

data class CompanyWithNetworksDTO(
    val idCompany: Int,
    val companyName: String,
    val companyDescription: String,
    val socialNetworks: List<SocialNetworkDTO>
)