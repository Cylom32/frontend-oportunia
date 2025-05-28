package com.example.oportunia.data.remote

import com.example.oportunia.data.remote.api.UniversitiesService
import com.example.oportunia.data.remote.dto.UniversityDTO
import retrofit2.Response
import javax.inject.Inject

class UniversitiesRemoteDataSource @Inject constructor(
    private val service: UniversitiesService
) {
    private suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): Result<T> =
        try {
            val response = apiCall()
            if (response.isSuccessful) {
                response.body()?.let { Result.success(it) }
                    ?: Result.failure(Exception("Response body was null"))
            } else {
                val err = response.errorBody()?.string()
                Result.failure(Exception("API error ${response.code()}: $err"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }

    suspend fun getAllUniversities(): Result<List<UniversityDTO>> =
        safeApiCall { service.getAllUniversities() }

    suspend fun getUniversityById(token: String, id: Int): Result<UniversityDTO> =
        safeApiCall { service.getUniversityById(token, id) }

    suspend fun createUniversity(token: String, dto: UniversityDTO): Result<UniversityDTO> =
        safeApiCall { service.createUniversity(token, dto) }

    suspend fun deleteUniversity(token: String, id: Int): Result<Unit> =
        safeApiCall { service.deleteUniversity(token, id) }
}
