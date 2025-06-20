package com.example.oportunia.data.mapper

import com.example.oportunia.data.remote.dto.StudentDTO
import com.example.oportunia.domain.model.Student
import javax.inject.Inject

class StudentMapper @Inject constructor() {
    fun mapToDomain(dto: StudentDTO): Student = Student(
        idStudent   = dto.idStudent ?: 0,
        idUser      = dto.idUser ?: 0,
        name        = dto.name ?: "",
        lastName1   = dto.lastName1 ?: "",
        lastName2   = dto.lastName2 ?: "",
        creationDate= dto.creationDate,
        universityId= dto.idUniversity
    )

    fun mapToDto(domain: Student): StudentDTO = StudentDTO(
        idStudent   = domain.idStudent,
        idUser      = domain.idUser,
        name        = domain.name,
        lastName1   = domain.lastName1,
        lastName2   = domain.lastName2,
        creationDate= domain.creationDate,
        idUniversity= domain.universityId
    )



}
