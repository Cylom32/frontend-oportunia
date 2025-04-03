package com.example.oportunia.data.remote.repository

import com.example.oportunia.data.mapper.UsersMapper
import com.example.oportunia.data.remote.UsersRemoteDataSource
import com.example.oportunia.domain.model.Users
import com.example.oportunia.domain.repository.UsersRepository
import kotlinx.coroutines.flow.first
import java.net.UnknownHostException
import javax.inject.Inject


/**
 * Implementation of [UsersRepository] that handles user data operations.
 * This repository coordinates between a remote data source and mapping DTOs to domain models.
 *
 * @property remoteDataSource Data source for remote user operations
 * @property usersMapper Mapper for converting between DTO and domain models
 */

class UsersRepositoryImpl @Inject constructor(
    private val remoteDataSource: UsersRemoteDataSource,
    private val usersMapper: UsersMapper
) : UsersRepository {

    override suspend fun findAllUsers(): Result<List<Users>> {
        return try {
            remoteDataSource.getAllUsers().map { dtoList ->
                dtoList.map { usersMapper.mapToDomain(it) }
            }
        } catch (e: UnknownHostException) {
            Result.failure(Exception("Network error: Cannot connect to server. Please check your internet connection."))
        } catch (e: Exception) {
            Result.failure(Exception("Error fetching users: ${e.message}"))
        }
    }

    override suspend fun findUserById(userId: Int): Result<Users> {
        return remoteDataSource.getUserById(userId).map {
            usersMapper.mapToDomain(it)
        }
    }

    override suspend fun saveUser(user: Users): Result<Unit> {
        return remoteDataSource.createUser(usersMapper.mapToDto(user)).map {}
    }

    override suspend fun findUserByEmail(email: String): Result<Users> {
        return Result.failure(Exception("Not implemented"))
    }

    override suspend fun loginUser(email: String, password: String): Result<Users> {
        return try {
            remoteDataSource.loginUser(email, password).map { dto ->
                usersMapper.mapToDomain(dto)
            }
        } catch (e: UnknownHostException) {
            Result.failure(Exception("Network error: Cannot connect to server. Please check your internet connection."))
        } catch (e: Exception) {
            Result.failure(Exception("Login failed: ${e.message}"))
        }
    }




}
