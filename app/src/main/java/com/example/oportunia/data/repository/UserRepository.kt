package com.example.oportunia.data.repository

import com.example.oportunia.data.datasource.network.UserService
import com.example.oportunia.domain.model.Users
import javax.inject.Inject

/**
 * Repository class for managing user data operations from remote API.
 *
 * @property userService The UserService instance for network operations.
 */
class RemoteUsersRepository @Inject constructor(
    private val userService: UserService
) {

    /**
     * Fetches all users from the remote API.
     *
     * @return A list of Users, or null if the request failed.
     */
    suspend fun getAllUsers(): List<Users>? {
        return try {
            val response = userService.getAllUsers(limit = 100)
            if (response.isSuccessful) response.body() ?: emptyList()
            else null
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    /**
     * Fetches a user by ID from the remote API.
     *
     * @param id The user ID to fetch.
     * @return The Users object, or null if not found or failed.
     */
    suspend fun getUserById(id: Int): Users? {
        return try {
            val response = userService.getUserById(id)
            if (response.isSuccessful) response.body()
            else null
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
