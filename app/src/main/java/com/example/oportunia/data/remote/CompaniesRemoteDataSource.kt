package com.example.oportunia.data.remote

import com.example.oportunia.data.remote.api.CompaniesService
import com.example.oportunia.data.remote.dto.*
import retrofit2.Response
import javax.inject.Inject

class CompaniesRemoteDataSource @Inject constructor(
    private val service: CompaniesService
) {
    private suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): Result<T> =
        try {
            val resp = apiCall()
            if (resp.isSuccessful) {
                resp.body()?.let { Result.success(it) }
                    ?: Result.failure(Exception("Empty body"))
            } else {
                Result.failure(Exception("API error: ${resp.code()} ${resp.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }

    suspend fun findAllCompanies(): Result<List<CompanyDTO>> =
        safeApiCall { service.getAllCompanies() }

    suspend fun findCompanyById(id: Int): Result<CompanyDTO> =
        safeApiCall { service.getCompanyById(id) }

}
