package com.example.oportunia.domain.repository

import com.example.oportunia.domain.model.SecurityUser

/**
 * This interface represents the SecurityUserRepository.
 */
interface SecurityUserRepository {
    suspend fun findAllSecurityUsers(): Result<List<SecurityUser>>
    suspend fun findSecurityUserById(securityUserId: Long): Result<SecurityUser>
}