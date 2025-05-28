package com.example.oportunia.data.mapper

import com.example.oportunia.data.remote.dto.UsersDTO
import com.example.oportunia.domain.model.Users
import java.time.LocalDate
import javax.inject.Inject
class UsersMapper @Inject constructor() {

    // DTO → dominio
    fun mapToDomain(dto: UsersDTO): Users = Users(
        id           = dto.idUser ?: 0,
        email        = dto.email,
        password     = dto.password,
        img          = dto.img,
        creationDate = LocalDate.parse(dto.creationDate),  // parse String → LocalDate
        roleId       = dto.idRole
    )

    // dominio → DTO
    fun mapToDto(domain: Users): UsersDTO = UsersDTO(
        idUser       = domain.id,
        email        = domain.email,
        password     = domain.password,
        img          = domain.img,
        creationDate = domain.creationDate.toString(),     // format LocalDate → String
        idRole       = domain.roleId
    )
}
