package com.example.oportunia.domain.repository

import com.example.oportunia.domain.model.User

/**
 * This interface represents the UserRepository.
 */
interface UserRepository {
    suspend fun findAllUsers(): Result<List<User>>
    suspend fun findUserById(userId: Long): Result<User>
}