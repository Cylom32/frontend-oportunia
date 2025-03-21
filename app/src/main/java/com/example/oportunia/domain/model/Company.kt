package com.example.oportunia.domain.model

/**
 * Represents a company.
 * @property id The unique identifier of the company.
 * @property companyName The name of the company.
 * @property companyDescription The description of the company.
 */
data class Company(
    val id: Int,
    val companyName: String,
    val companyDescription: String
)

