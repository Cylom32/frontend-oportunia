package com.example.oportunia.domain.repository

import com.example.oportunia.domain.model.Role

/**
 * This interface represents the RoleRepository.
 */
interface RoleRepository {
    suspend fun findAllRoles(): Result<List<Role>>
    suspend fun findRoleById(roleId: Long): Result<Role>
}