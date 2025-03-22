package com.example.oportunia.data.datasource



import com.example.oportunia.data.datasource.model.UsersDTO
import com.example.oportunia.data.mapper.UsersMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Implementation of UsersDataSource using local static data from UsersProvider.
 * @param usersMapper Mapper for converting between Users and UsersDTO.
 */
class UsersDataSourceImple(
    private val usersMapper: UsersMapper = UsersMapper()
) : UsersDataSource {

    override suspend fun getUsers(): Flow<List<UsersDTO>> = flow {
        val users = UsersProvider.findAllUsers()
        emit(users.map { usersMapper.mapToDto(it) })
    }

    override suspend fun getUserById(id: Int): UsersDTO? {
        val user = UsersProvider.findUserById(id)
        return user?.let { usersMapper.mapToDto(it) }
    }

    override suspend fun insertUser(userDTO: UsersDTO) {
        // No-op: mock implementation
    }

    override suspend fun updateUser(userDTO: UsersDTO) {
        // No-op: mock implementation
    }

    override suspend fun deleteUser(userDTO: UsersDTO) {
        // No-op: mock implementation
    }

    override suspend fun getUserByEmail(email: String): UsersDTO? {
        val user = UsersProvider.findAllUsers().find { it.email == email }
        return user?.let { usersMapper.mapToDto(it) }
    }
}
