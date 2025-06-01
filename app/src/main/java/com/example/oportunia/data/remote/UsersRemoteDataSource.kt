package com.example.oportunia.data.remote

import com.example.oportunia.data.remote.api.UsersService
import com.example.oportunia.data.remote.dto.AreaDTO
import com.example.oportunia.data.remote.dto.CreateUserCompanyInput
import com.example.oportunia.data.remote.dto.CreateUserCompanyResponse
import com.example.oportunia.data.remote.dto.InboxxInput
import com.example.oportunia.data.remote.dto.InboxxResponse
import com.example.oportunia.data.remote.dto.LocationDTO
import com.example.oportunia.data.remote.dto.UserEmailResponse
import com.example.oportunia.data.remote.dto.UserResponseDTO


import com.example.oportunia.data.remote.dto.UsersDTO
import com.example.oportunia.domain.model.CreateCompanyInput
import com.example.oportunia.domain.model.CreateCompanyResponse
import retrofit2.Response
import javax.inject.Inject

/**
 * Remote data source for user operations.
 * Handles all network operations related to users using [UsersService].
 *
 * @property usersService The service interface for user-related API calls
 */
class UsersRemoteDataSource @Inject constructor(
    private val usersService: UsersService
) {
    /**
     * Retrieves all users from the remote API.
     */
    suspend fun getAllUsers(): Result<List<UsersDTO>> = safeApiCall {
        //println("🟢 Llamando a la API desde getAllUsers()")
        usersService.getAllUsers()
    }


    /**
     * Retrieves a specific user by ID.
     */
    suspend fun getUserById(id: Int): Result<UsersDTO> = safeApiCall {
        usersService.getUserById(id)
    }

    /**
     * Creates a new user.
     */
    suspend fun createUser(userDto: UsersDTO): Result<UserResponseDTO> = safeApiCall {
        usersService.createUser(userDto)
    }

    suspend fun createUserCompany(
        input: CreateUserCompanyInput
    ): Result<CreateUserCompanyResponse> =
        safeApiCall { usersService.createUserCompany(input) }

    /**
     * Updates an existing user.
     */
    suspend fun updateUser(id: Int, userDto: UsersDTO): Result<UsersDTO> = safeApiCall {
        usersService.updateUser(id, userDto)
    }

    /**
     * Deletes a user by ID.
     */
    suspend fun deleteUser(id: Int): Result<Unit> = safeApiCall {
        usersService.deleteUser(id)
    }

    /**
     * Helper function to safely handle API calls.
     */
    private suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): Result<T> = try {
        val response = apiCall()
        if (response.isSuccessful) {
            response.body()?.let {
                Result.success(it)
            } ?: Result.failure(Exception("Response body was null"))
        } else {
            val errorBody = response.errorBody()?.string()
            Result.failure(Exception("API error ${response.code()}: $errorBody"))
        }
    } catch (e: Exception) {
        Result.failure(e)
    }

    /**
     * Attempts to log in a user by verifying credentials.
     */
    suspend fun loginUser(email: String, password: String): Result<UsersDTO> {
        val responseResult = safeApiCall {
            usersService.loginUser(email, password)
        }

        return responseResult.mapCatching { users ->
            if (users.isNotEmpty()) {
                users.first()
            } else {
                throw Exception("Credenciales inválidas.")
            }
        }
    }


    suspend fun getUserByEmail(
        email: String
    ): Result<UserEmailResponse> = safeApiCall {
        usersService.getUserByEmail(email)
    }


    suspend fun getAreas(): Result<List<AreaDTO>> = safeApiCall {
        usersService.getAreas()
    }

    suspend fun getAllLocations(): Result<List<LocationDTO>> = safeApiCall {
        usersService.getAllLocations()

    }

    suspend fun createCompany(
        input: CreateCompanyInput
    ): Result<CreateCompanyResponse> = safeApiCall {
        usersService.createCompany(input)
    }


    suspend fun createInboxx(input: InboxxInput): Result<InboxxResponse> =
        safeApiCall { usersService.createInboxx(input) }


}