package com.example.oportunia.data.remote.dto



data class CompanyResponseU(
    val idCompany: Int,
    val companyName: String,
    val companyDescription: String,
    val user: User
) {
    data class User(
        val idUser: Int,
        val email: String,
        val password: String,
        val img: String,
        val creationDate: String,
        val role: Role
    )

    data class Role(
        val idRole: Int,
        val name: String
    )
}
