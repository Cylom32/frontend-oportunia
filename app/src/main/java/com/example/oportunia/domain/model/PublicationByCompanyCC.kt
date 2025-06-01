package com.example.oportunia.domain.model



data class PublicationByCompanyCC(
    val id: Int,
    val file: String,
    val paid: Boolean,
    val location: LocationCC,
    val area: AreaCC,
    val company: CompanyCC
)

data class LocationCC(
    val idLocation: Int,
    val name: String
)

data class AreaCC(
    val idArea: Int,
    val name: String
)

data class CompanyCC(
    val idCompany: Int,
    val companyName: String,
    val companyDescription: String,
    val user: UserCC
)

data class UserCC(
    val idUser: Int,
    val email: String,
    val password: String,
    val img: String,
    val creationDate: String,
    val role: RoleCC
)

data class RoleCC(
    val idRole: Int,
    val name: String
)
