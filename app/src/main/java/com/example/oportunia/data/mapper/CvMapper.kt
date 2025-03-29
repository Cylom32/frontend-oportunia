package com.example.oportunia.data.mapper

import com.example.oportunia.data.datasource.model.CvDTO
import com.example.oportunia.domain.model.Cv

class CvMapper {
    fun mapToDomain(dto: CvDTO): Cv = Cv(
        id = dto.id ?: 0,
        name = dto.name ?: "",
        file = dto.file ?: "",
        studentId = dto.studentId ?: 0,
        status = dto.status ?: false
    )

    fun mapToDto(domain: Cv): CvDTO = CvDTO(
        id = domain.id,
        name = domain.name,
        file = domain.file,
        studentId = domain.studentId,
        status = domain.status
    )
}
