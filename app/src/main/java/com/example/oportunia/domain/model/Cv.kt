package com.example.oportunia.domain.model

/**
 * Represents a CV.
 * @property id The unique identifier of the CV.
 * @property name The name of the CV.
 * @property file The file path or content of the CV.
 * @property studentId The student associated with the CV.
 * @property status Indicates whether the CV is currently active.
 */
data class Cv(
    val id: Int,
    val name: String,
    val file: String,
    val studentId: Int,
    val status: Boolean
)
