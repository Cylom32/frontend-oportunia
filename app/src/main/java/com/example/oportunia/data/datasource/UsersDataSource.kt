package com.example.oportunia.data.datasource

import com.example.oportunia.data.datasource.model.UsersDTO
import kotlinx.coroutines.flow.Flow


interface UsersDataSource {
    suspend fun getUsers(): Flow<List<UsersDTO>>
    suspend fun getUserById(id: Int): UsersDTO?
    suspend fun insertUser(userDTO: UsersDTO)
    suspend fun updateUser(userDTO: UsersDTO)
    suspend fun deleteUser(userDTO: UsersDTO)
    suspend fun getUserByEmail(email: String): UsersDTO?
}