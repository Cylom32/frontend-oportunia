package com.example.oportunia.domain.model

/**
 * Represents a publication.
 * @property id The unique identifier of the publication.
 * @property file The file path or content of the publication.
 * @property locationId The location associated with the publication.
 * @property areaId The area associated with the publication.
 */
data class Publication(
    val id: Int,
    val file: String,
    val locationId: Int,
    val areaId: Int
)
