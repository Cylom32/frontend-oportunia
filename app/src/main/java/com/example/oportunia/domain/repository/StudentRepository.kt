package com.example.oportunia.domain.repository

import com.example.oportunia.domain.model.Student

/**
 * This interface represents the StudentRepository.
 */
interface StudentRepository {
    suspend fun findAllStudents(): Result<List<Student>>
    suspend fun findStudentById(studentId: Int): Result<Student>
    suspend fun findStudentByIdUser(idUser: Int): Result<Student>
    suspend fun insertStudent(student: Student): Result<Unit>
}