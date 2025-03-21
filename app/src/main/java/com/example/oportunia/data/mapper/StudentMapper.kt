package com.example.oportunia.data.mapper

import com.example.oportunia.data.datasource.model.StudentDTO
import com.example.oportunia.domain.model.Student

class StudentMapper {
    fun mapToDomain(dto: StudentDTO): Student = Student(
        id = dto.idStudent ?: 0,
        name = dto.name ?: "",
        lastName1 = dto.lastName1 ?: "",
        lastName2 = dto.lastName2 ?: "",
        creationDate = dto.creationDate,
        universityId = dto.idUniversity
    )

    fun mapToDto(domain: Student): StudentDTO = StudentDTO(
        idStudent = domain.id,
        name = domain.name,
        lastName1 = domain.lastName1,
        lastName2 = domain.lastName2,
        creationDate = domain.creationDate,
        idUniversity = domain.universityId
    )
}
