package com.example.oportunia.data.mapper

import com.example.oportunia.data.remote.dto.UniversityDTO
import com.example.oportunia.domain.model.University
import javax.inject.Inject

class UniversityMapper @Inject constructor() {
    fun mapToDomain(dto: UniversityDTO): University = University(
        idUniversity   = dto.idUniversity ?: 0,
        universityName = dto.universityName ?: ""
    )

    fun mapToDto(domain: University): UniversityDTO = UniversityDTO(
        idUniversity   = domain.idUniversity,
        universityName = domain.universityName
    )
}
