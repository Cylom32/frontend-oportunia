package com.example.oportunia.data.remote.dto

data class PublicationDetailDTO(
    val id: Int,
    val file: String,
    val paid: Boolean,
    val location: LocationDTO,
    val area: AreaDTO,
    val company: CompanyDTO
)
