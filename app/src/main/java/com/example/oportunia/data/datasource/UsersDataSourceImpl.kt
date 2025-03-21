package com.example.oportunia.data.datasource



import com.example.oportunia.data.mapper.UsersMapper
import com.example.oportunia.domain.model.Users
import com.example.oportunia.domain.repository.UserRepository
import kotlinx.coroutines.flow.first

class UsersDataSourceImpl(
    private val dataSource: UsersDataSource,
    private val mapper: UsersMapper
) : UserRepository {

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
}
