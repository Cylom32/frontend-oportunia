package com.example.oportunia.data.remote.dto

data class PublicationFilterDTO(
    val id: Int,
    val file: String,
    val paid: Boolean,
    val location: LocationDTO,
    val area: AreaDTO,
    val company: CompanyDetailDTO
)
