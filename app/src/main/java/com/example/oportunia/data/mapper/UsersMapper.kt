package com.example.oportunia.data.mapper

import com.example.oportunia.data.remote.dto.UsersDTO
import com.example.oportunia.domain.model.Users
import java.time.LocalDate
import javax.inject.Inject

class UsersMapper @Inject constructor() {
    fun mapToDomain(dto: UsersDTO): Users = Users(
        id = dto.idUser ?: 0,
        email = dto.email,
        password = dto.password,
        img = dto.img,
        creationDate = dto.creationDate ?: LocalDate.now(),
        roleId = dto.idRole
    )

    fun mapToDto(domain: Users): UsersDTO = UsersDTO(
        idUser = domain.id,
        email = domain.email,
        password = domain.password,
        img = domain.img,
        creationDate = domain.creationDate,
        idRole = domain.roleId
    )
}
