package com.example.oportunia.data.repository

import com.example.oportunia.data.datasource.StudentDataSource

import com.example.oportunia.data.mapper.StudentMapper
import com.example.oportunia.data.mapper.UsersMapper
import com.example.oportunia.domain.model.Student
import com.example.oportunia.domain.model.Users
import com.example.oportunia.domain.repository.StudentRepository
import kotlinx.coroutines.flow.first

class StudentRepositoryImpl(
    private val dataSource: StudentDataSource,
    private val mapper: StudentMapper

):StudentRepository {
    override suspend fun findAllStudents(): Result<List<Student>> = runCatching {
        dataSource.getStudents().first().map { dto ->
            mapper.mapToDomain(dto)
        }
    }


    override suspend fun findStudentById(studentId: Int): Result<Student> = runCatching {
        val dto = dataSource.getStudentById(studentId.toInt()) ?: error("User not found")
        mapper.mapToDomain(dto)
    }

    override suspend fun findStudentByIdUser(studentId: Int): Result<Student> = runCatching {
        val dto = dataSource.getStudentById(studentId.toInt()) ?: error("User not found")
        mapper.mapToDomain(dto)
    }


    override suspend fun insertStudent(student: Student): Result<Unit> = runCatching {
        val dto = mapper.mapToDto(student)
        dataSource.insertStudent(dto)
    }

    override suspend fun updateStudent(student: Student): Result<Unit> = runCatching {
        val dto = mapper.mapToDto(student)
        dataSource.updateStudent(dto)
    }


}