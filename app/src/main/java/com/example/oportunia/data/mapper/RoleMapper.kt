package com.example.oportunia.data.mapper

import com.example.oportunia.data.datasource.model.RoleDTO
import com.example.oportunia.domain.model.Role


class RoleMapper {
    fun mapToDomain(dto: RoleDTO): Role = Role(
        id = dto.idRole ?: 0,
        name = dto.name ?: ""
    )

    fun mapToDto(domain: Role): RoleDTO = RoleDTO(
        idRole = domain.id,
        name = domain.name
    )
}
