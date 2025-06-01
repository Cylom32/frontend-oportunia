package com.example.oportunia.domain.repository

import com.example.oportunia.domain.model.Users

import com.example.oportunia.data.remote.dto.AuthResponseDto
import com.example.oportunia.data.remote.dto.CreateUserCompanyInput
import com.example.oportunia.data.remote.dto.CreateUserCompanyResponse
import com.example.oportunia.data.remote.dto.InboxxInput
import com.example.oportunia.data.remote.dto.InboxxResponse
import com.example.oportunia.data.remote.dto.StudentResult
import com.example.oportunia.data.remote.dto.UserEmailResponse
import com.example.oportunia.data.remote.dto.UserWhitoutId
import com.example.oportunia.data.remote.dto.UsersDTO
import com.example.oportunia.domain.model.Area
import com.example.oportunia.domain.model.AuthResult
import com.example.oportunia.domain.model.CreateCompanyInput
import com.example.oportunia.domain.model.CreateCompanyResponse
import com.example.oportunia.domain.model.Location

/**
 * This interface represents the UserRepository.
 */
interface UsersRepository {
    suspend fun findAllUsers(): Result<List<Users>>
    suspend fun findUserById(userId: Int): Result<Users>
   // suspend fun findUserByEmail(email: String): Result<Users>
    suspend fun saveUser(user: Users): Result<Unit>
    suspend fun loginUser(email: String, password: String): Result<AuthResult>
    suspend fun findUserByEmail(email: String): Result<UserEmailResponse>
    suspend fun saveUserNoId(user: UserWhitoutId): Result<Users>

    suspend fun findAllAreas(): Result<List<Area>>
    suspend fun findAllLocations(): Result<List<Location>>

    suspend fun createUserCompany(
        input: CreateUserCompanyInput
    ): Result<CreateUserCompanyResponse>

    suspend fun createCompany(
        input: CreateCompanyInput
    ): Result<CreateCompanyResponse>

    suspend fun createInboxx(input: InboxxInput): Result<InboxxResponse>


}