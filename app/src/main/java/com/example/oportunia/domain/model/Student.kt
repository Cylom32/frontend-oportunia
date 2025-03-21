package com.example.oportunia.domain.model

import java.util.Date

/**
 * Represents a student.
 * @property id The unique identifier of the student.
 * @property name The first name of the student.
 * @property lastName1 The first last name of the student.
 * @property lastName2 The second last name of the student.
 * @property creationDate The date when the student record was created.
 * @property universityId The university associated with the student.
 */
data class Student(
    val id: Int,
    val name: String,
    val lastName1: String,
    val lastName2: String,
    val creationDate: Date?,
    val universityId: Int?
)