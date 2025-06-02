package com.example.oportunia.data.remote

import com.example.oportunia.data.remote.api.CompaniesService
import com.example.oportunia.data.remote.dto.*
import com.example.oportunia.domain.model.MessageInput
import com.example.oportunia.domain.model.MessageResponseS
import com.example.oportunia.domain.model.UserResponseCompany
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

    suspend fun findPublicationsByFilter(
        areaId: Int? = null,
        locationId: Int? = null,
        paid: Boolean? = null
    ): Result<List<PublicationFilterDTO>> = try {
        val resp = service.getPublicationsByFilter(areaId, locationId, paid)
        if (resp.isSuccessful) Result.success(resp.body() ?: emptyList())
        else Result.failure(Exception("HTTP ${resp.code()}"))
    } catch (t: Throwable) {
        Result.failure(Exception("Red: ${t.localizedMessage}"))
    }


    suspend fun findWithNetworks(id: Int): CompanyWithNetworksDTO {
        val resp = service.getCompanyWithNetworks(id)
        if (resp.isSuccessful) {
            return resp.body()!!
        } else {
            throw Exception("Error ${resp.code()} al obtener redes de company=$id: ${resp.message()}")
        }
    }

    suspend fun findPublicationsByCompany(
        token: String,
        companyId: Int
    ): Result<List<PublicationByCompanyDTO>> =
        safeApiCall { service.getPublicationsByCompany(token, companyId) }

    suspend fun findPublicationById(publicationId: Int): Result<PublicationDetailDTO> =
        safeApiCall { service.getPublicationById(publicationId) }

    suspend fun findInboxByCompany(companyId: Int): Result<InboxResult> =
        safeApiCall { service.getInboxByCompany(companyId) }


    suspend fun findCvListByStudent(
        token: String,
        studentId: Int
    ): Result<List<CVListResponse>> =
        safeApiCall { service.getCvListByStudent(token, studentId) }


    suspend fun sendMessage(
        token: String,
        input: MessageInput
    ): Result<Unit> =
        try {
            val resp = service.sendMessage(token, input)
            if (resp.isSuccessful) Result.success(Unit)
            else Result.failure(Exception("HTTP ${resp.code()} ${resp.message()}"))
        } catch (e: Exception) {
            Result.failure(e)
        }


    suspend fun findMessagesByStudent(
        token: String,
        studentId: Int
    ): Result<List<MessageResponseS>> =
        safeApiCall { service.getMessagesByStudent(token, studentId) }


    suspend fun findCompanyByUser(userId: Int): Result<CompanyResponseU> =
        safeApiCall { service.getCompanyByUser(userId) }


    suspend fun findSocialNetworksByCompany(companyId: Int): Result<List<com.example.oportunia.domain.model.SocialNetwork>> =
        safeApiCall { service.getSocialNetworksByCompany(companyId) }

    suspend fun findUserCompanyById(token: String, userIdCompany: Int): Result<UserResponseCompany> =
        safeApiCall { service.getUserCompanyById(token, userIdCompany) }

    suspend fun deletePublicationById(token: String, publicationId: Int): Result<Unit> =
        try {
            val resp = service.deletePublicationById(token, publicationId)
            if (resp.isSuccessful) Result.success(Unit)
            else Result.failure(Exception("Error ${resp.code()} ${resp.message()}"))
        } catch (e: Exception) {
            Result.failure(e)
        }



}
