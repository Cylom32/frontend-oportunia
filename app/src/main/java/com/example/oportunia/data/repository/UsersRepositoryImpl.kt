package com.example.oportunia.data.repository

import com.example.oportunia.data.mapper.UsersMapper
import com.example.oportunia.data.remote.UsersRemoteDataSource
import com.example.oportunia.domain.model.Users
import com.example.oportunia.domain.repository.UsersRepository
import java.net.UnknownHostException
import javax.inject.Inject

class UsersRepositoryImpl @Inject constructor(
    private val remoteDataSource: UsersRemoteDataSource,
    private val mapper: UsersMapper
) : UsersRepository {

    override suspend fun findAllUsers(): Result<List<Users>> {
        return try {
            remoteDataSource.getAllUsers().map { dtoList ->
                dtoList.map { mapper.mapToDomain(it) }
            }
        } catch (e: UnknownHostException) {
            Result.failure(Exception("Network error: cannot connect to server."))
        } catch (e: Exception) {
            Result.failure(Exception("Error fetching users: ${e.message}"))
        }
    }

    override suspend fun findUserById(userId: Int): Result<Users> =
        remoteDataSource.getUserById(userId).map { mapper.mapToDomain(it) }

    override suspend fun saveUser(user: Users): Result<Unit> =
        remoteDataSource.createUser(mapper.mapToDto(user)).map {}

    override suspend fun findUserByEmail(email: String): Result<Users> {
        return Result.failure(Exception("Not implemented"))
    }

    override suspend fun loginUser(email: String, password: String): Result<Users> {
        return try {
            remoteDataSource.loginUser(email, password).map { dto ->
                mapper.mapToDomain(dto)
            }
        } catch (e: UnknownHostException) {
            Result.failure(Exception("Network error: cannot connect to server."))
        } catch (e: Exception) {
            Result.failure(Exception("Login failed: ${e.message}"))
        }
    }

}
