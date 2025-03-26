package com.example.oportunia.data.datasource

import com.example.oportunia.data.datasource.model.StudentDTO
import com.example.oportunia.data.datasource.model.UsersDTO
import kotlinx.coroutines.flow.Flow

interface StudentDataSource {
    suspend fun getStudents(): Flow<List<StudentDTO>>
    suspend fun getStudentById(id: Int): StudentDTO?
    suspend fun insertStudent(userDTO: StudentDTO)
    suspend fun updateStudent(userDTO: StudentDTO)
    suspend fun deleteStudent(userDTO: StudentDTO)
    suspend fun findStudentByUserId(userId: Int): StudentDTO?
    suspend fun getStudentByUserId(userId: Int): StudentDTO?
}
