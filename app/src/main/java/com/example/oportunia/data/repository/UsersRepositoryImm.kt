package com.example.oportunia.data.repository



import com.example.oportunia.data.mapper.UsersMapper
import com.example.oportunia.data.remote.UsersRemoteDataSource
import com.example.oportunia.domain.model.Users
import com.example.oportunia.domain.repository.UsersRepository
import java.net.UnknownHostException
import javax.inject.Inject


/**
 * Implementation of [UsersRepository] that handles user data operations.
 * This repository acts as a single source of truth for user data,
 * coordinating between remote data sources and mapping DTOs to domain models.
 *
 * @property remoteDataSource Data source for remote user operations
 * @property usersMapper Mapper for converting between DTO and domain models
 */
//class UsersRepositoryImm @Inject constructor(
//    private val remoteDataSource: UsersRemoteDataSource,
//    private val usersMapper: UsersMapper
//) : UsersRepository {
//
//    /**
//     * Retrieves all users from the remote data source.
//     */
//    override suspend fun findAllUsers(): Result<List<Users>> {
//        return try {
//            remoteDataSource.getAllUsers().map { userDtos ->
//                userDtos.map { usersMapper.mapToDomain(it) }
//            }
//        } catch (e: UnknownHostException) {
//            Result.failure(Exception("Network error: Cannot connect to server. Please check your internet connection."))
//        } catch (e: Exception) {
//            Result.failure(Exception("Error fetching users: ${e.message}"))
//        }
//    }
//
//    /**
//     * Retrieves a specific user by ID.
//     */
//    override suspend fun findUserById(userId: Int): Result<Users> =
//        remoteDataSource.getUserById(userId).map { dto ->
//            usersMapper.mapToDomain(dto)
//        }
//
//    /**
//     * Creates a new user.
//     */
//    override suspend fun saveUser(user: Users): Result<Unit> =
//        remoteDataSource.createUser(usersMapper.mapToDto(user)).map { }
//
//    /**
//     * Finds a user by their email.
//     * NOTE: This assumes you will implement this endpoint.
//     */
//    override suspend fun findUserByEmail(email: String): Result<Users> {
//        return Result.failure(Exception("Not implemented: findUserByEmail"))
//    }
//}