package com.example.oportunia.data.datasource

import com.example.oportunia.data.datasource.model.StudentDTO

import com.example.oportunia.data.mapper.StudentMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class StudentDataSourceImple(  private val studentMapper: StudentMapper= StudentMapper()
):StudentDataSource {

    override suspend fun getStudents(): Flow<List<StudentDTO>> = flow {
        val student = StudentProvider.findAllStudents()
        emit(student.map { studentMapper.mapToDto(it) })
    }

    override suspend fun getStudentById(id: Int): StudentDTO? {
        val user = StudentProvider.findStudentByUserId(id)
        return user?.let { studentMapper.mapToDto(it) }
    }

    override suspend fun insertStudent(studentDTO: StudentDTO) {

    }

    override suspend fun getStudentByUserId(userId: Int): StudentDTO? {
        val student = StudentProvider.findStudentByUserId(userId)
        return student?.let { studentMapper.mapToDto(it) }
    }


    override suspend fun updateStudent(studentDTO: StudentDTO) {

    }

    override suspend fun deleteStudent(studentDTO: StudentDTO) {

    }

    override suspend fun findStudentByUserId(id: Int): StudentDTO? {
        val user = StudentProvider.findStudentByUserId(id)
        return user?.let { studentMapper.mapToDto(it) }
    }





}