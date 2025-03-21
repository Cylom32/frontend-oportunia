package com.example.oportunia.domain.repository

import com.example.oportunia.domain.model.Student

/**
 * This interface represents the StudentRepository.
 */
interface StudentRepository {
    suspend fun findAllStudents(): Result<List<Student>>
    suspend fun findStudentById(studentId: Long): Result<Student>
}