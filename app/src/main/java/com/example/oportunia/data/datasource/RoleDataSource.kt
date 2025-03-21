package com.example.oportunia.data.datasource

import com.example.oportunia.data.datasource.model.RoleDTO
import kotlinx.coroutines.flow.Flow

interface RoleDataSource {
    suspend fun getRoles(): Flow<List<RoleDTO>>
    suspend fun getRoleById(id: Int): RoleDTO?
}
