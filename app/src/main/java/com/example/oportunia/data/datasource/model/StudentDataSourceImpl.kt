package com.example.oportunia.data.datasource.model

import com.example.oportunia.data.datasource.StudentDataSource
import com.example.oportunia.data.mapper.StudentMapper
import com.example.oportunia.domain.model.Student
import com.example.oportunia.domain.repository.StudentRepository
import kotlinx.coroutines.flow.first

class StudentDataSourceImpl(
    private val dataSource: StudentDataSource,
    private val mapper: StudentMapper
) : StudentRepository {

    override suspend fun findAllStudents(): Result<List<Student>> = runCatching {
        dataSource.getStudents().first().map { dto ->
            mapper.mapToDomain(dto)
        }
    }

    override suspend fun findStudentById(studentId: Int): Result<Student> = runCatching {
        val dto = dataSource.getStudentById(studentId.toInt()) ?: error("Student not found")
        mapper.mapToDomain(dto)
    }

    override suspend fun findStudentByIdUser(idUser: Int): Result<Student> = runCatching {
        val dto = dataSource.getStudentByUserId(idUser.toInt()) ?: error("Student not found for user ID")
        mapper.mapToDomain(dto)
    }
}
