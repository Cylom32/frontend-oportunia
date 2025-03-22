package com.example.oportunia.data.repository


import com.example.oportunia.data.datasource.UsersDataSource
import com.example.oportunia.data.mapper.UsersMapper
import com.example.oportunia.domain.model.Users
import com.example.oportunia.domain.repository.UsersRepository
import kotlinx.coroutines.flow.first

/**
 * Implementation of [UsersRepository] that handles user data operations.
 * Maps between UsersDTO and Users domain model using [UsersMapper].
 *
 * @property dataSource The data source for user operations.
 * @property mapper The mapper for converting between DTO and domain model.
 */
class UsersRepositoryImpl(
    private val dataSource: UsersDataSource,
    private val mapper: UsersMapper
) : UsersRepository {

    override suspend fun findAllUsers(): Result<List<Users>> = runCatching {
        dataSource.getUsers().first().map { dto ->
            mapper.mapToDomain(dto)
        }
    }

    override suspend fun findUserById(userId: Long): Result<Users> = runCatching {
        val dto = dataSource.getUserById(userId.toInt()) ?: error("User not found")
        mapper.mapToDomain(dto)
    }

    override suspend fun saveUser(user: Users): Result<Unit> = runCatching {
        dataSource.insertUser(mapper.mapToDto(user))
    }

    override suspend fun findUserByEmail(email: String): Result<Users> = runCatching {
        val dto = dataSource.getUserByEmail(email) ?: error("User not found")
        mapper.mapToDomain(dto)
    }


}
