package com.example.oportunia.domain.repository

import com.example.oportunia.domain.model.Users

/**
 * This interface represents the UserRepository.
 */
interface UsersRepository {
    suspend fun findAllUsers(): Result<List<Users>>
    suspend fun findUserById(userId: Int): Result<Users>
    suspend fun findUserByEmail(email: String): Result<Users>
    suspend fun saveUser(user: Users): Result<Unit>
    suspend fun loginUser(email: String, password: String): Result<Users>

}