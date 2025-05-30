package com.example.oportunia.data.remote.repository

import com.example.oportunia.data.mapper.UsersMapper
import com.example.oportunia.data.remote.AuthRemoteDataSource
import com.example.oportunia.data.remote.UsersRemoteDataSource
import com.example.oportunia.data.remote.dto.AuthResponseDto
import com.example.oportunia.data.remote.dto.UserEmailResponse
import com.example.oportunia.data.remote.dto.UserResponseDTO
import com.example.oportunia.data.remote.dto.UserWhitoutId
import com.example.oportunia.data.remote.dto.UsersDTO
import com.example.oportunia.domain.model.Area
import com.example.oportunia.domain.model.AuthResult
import com.example.oportunia.domain.model.Users
import com.example.oportunia.domain.repository.UsersRepository
import kotlinx.coroutines.flow.first
import java.net.UnknownHostException
import javax.inject.Inject

import com.example.oportunia.domain.model.Credentials
import com.example.oportunia.domain.model.Location
import java.time.LocalDate
import java.time.format.DateTimeFormatter


/**
 * Implementation of [UsersRepository] that handles user data operations.
 * This repository coordinates between a remote data source and mapping DTOs to domain models.
 *
 * @property remoteDataSource Data source for remote user operations
 * @property usersMapper Mapper for converting between DTO and domain models
 */

class UsersRepositoryImpl @Inject constructor(
    private val remoteDataSource: UsersRemoteDataSource,
    private val usersMapper: UsersMapper,
    private val authRemoteDataSource: AuthRemoteDataSource
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
//
//    override suspend fun findUserByEmail(email: String): Result<Users> {
//        return Result.failure(Exception("Not implemented"))
//    }



    override suspend fun loginUser(email: String, password: String): Result<AuthResult> {

        val creds = Credentials(username = email, password = password)

        return authRemoteDataSource.login(creds)
    }

    override suspend fun findUserByEmail(email: String): Result<UserEmailResponse> {

        return try {
            remoteDataSource.getUserByEmail(email)
        } catch (e: Exception) {
            Result.failure(Exception("Error buscando usuario: ${e.message}"))
        }
    }

    override suspend fun saveUserNoId(user: UserWhitoutId): Result<Users> =
        runCatching {
            // 1) Construye tu DTO de petición (sin id)
            val requestDto = UsersDTO(
                email        = user.email,
                password     = user.password,
                img          = user.img,
                creationDate = user.creationDate.toString(),
                idRole       = user.idRole
            )

            // 2) Lanza la llamada y recibe el DTO de respuesta
            val resp: UserResponseDTO = remoteDataSource
                .createUser(requestDto)
                .getOrThrow()

            // 3) Convierte a tu modelo de dominio y lo devuelves
            Users(
                id           = resp.idUser,
                email        = resp.email,
                password     = resp.password,
                img          = resp.img,
                creationDate = LocalDate.parse(
                    resp.creationDate.substring(0, 10)
                ),  // toma solo "YYYY-MM-DD"
                roleId       = resp.role.idRole
            )
        }


    override suspend fun findAllAreas(): Result<List<Area>> {
        return try {
            // Llama al data source y convierte DTO → modelo de dominio
            val dtos = remoteDataSource.getAreas().getOrThrow()
            val areas = dtos.map { dto ->
                Area(id = dto.idArea, name = dto.name)
            }
            Result.success(areas)
        } catch (e: Throwable) {
            Result.failure(Exception("Error al cargar áreas: ${e.message}"))
        }
    }


    override suspend fun findAllLocations(): Result<List<Location>> {
        return try {
            remoteDataSource.getAllLocations().map { dtoList ->
                dtoList.map { dto ->
                    Location(id = dto.idLocation, name = dto.name)
                }
            }
        } catch (e: Exception) {
            Result.failure(Exception("Error al obtener ubicaciones: ${e.message}"))
        }
    }


}




