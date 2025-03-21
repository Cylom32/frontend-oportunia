package com.example.oportunia.data.datasource

import com.example.oportunia.data.datasource.model.StudentDTO
import kotlinx.coroutines.flow.Flow

interface StudentDataSource {
    suspend fun getStudents(): Flow<List<StudentDTO>>
    suspend fun getStudentById(id: Int): StudentDTO?
}
