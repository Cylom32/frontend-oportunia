package com.example.oportunia.data.remote.api

import com.example.oportunia.data.remote.dto.*
import com.example.oportunia.domain.model.MessageInput
import com.example.oportunia.domain.model.MessageResponseS
import com.example.oportunia.domain.model.UserResponseCompany
import retrofit2.Response
import retrofit2.http.*

interface CompaniesService {

    @GET("v1/companies")
    suspend fun getAllCompanies(): Response<List<CompanyDTO>>

    @GET("v1/companies/{id}")
    suspend fun getCompanyById(
        @Path("id") id: Int
    ): Response<CompanyDTO>

    // CompaniesService.kt (dentro de la interfaz CompaniesService, donde está el “/// aquiiiii”)
    @GET("v1/publications/filters")
    suspend fun getPublicationsByFilter(
        @Query("areaId") areaId: Int? = null,
        @Query("locationId") locationId: Int? = null,
        @Query("paid") paid: Boolean? = null
    ): Response<List<PublicationFilterDTO>>


    @GET("v1/companies/{id}/social-networks")
    suspend fun getCompanyWithNetworks(
        @Path("id") id: Int
    ): Response<CompanyWithNetworksDTO>


    @GET("v1/publications/by-company/{companyId}")
    suspend fun getPublicationsByCompany(
        @Header("Authorization") token: String,
        @Path("companyId") companyId: Int
    ): Response<List<PublicationByCompanyDTO>>

    @GET("v1/publications/{publicationId}")
    suspend fun getPublicationById(
        @Path("publicationId") publicationId: Int
    ): Response<PublicationDetailDTO>


    @GET("v1/inboxes/{companyId}")
    suspend fun getInboxByCompany(@Path("companyId") companyId: Int): Response<InboxResult>

    @GET("v1/cvs/students/{student_id}")
    suspend fun getCvListByStudent(
        @Header("Authorization") token: String,
        @Path("student_id") studentId: Int
    ): Response<List<CVListResponse>>


    @POST("v1/messages")
    suspend fun sendMessage(
        @Header("Authorization") token: String,
        @Body input: MessageInput
    ): Response<Unit>


    @GET("v1/messages/by-student/{student_id}")
    suspend fun getMessagesByStudent(
        @Header("Authorization") token: String,
        @Path("student_id") studentId: Int
    ): Response<List<MessageResponseS>>


    // CompaniesService.kt
    @GET("v1/companies/user/{userId}")
    suspend fun getCompanyByUser(@Path("userId") userId: Int): Response<CompanyResponseU>

    @GET("v1/social_networks/company/{companyId}")
    suspend fun getSocialNetworksByCompany(
        @Path("companyId") companyId: Int
    ): Response<List<com.example.oportunia.domain.model.SocialNetwork>>

    @GET("v1/users/{user_id_company}")
    suspend fun getUserCompanyById(
        @Header("Authorization") token: String,
        @Path("user_id_company") userIdCompany: Int
    ): Response<UserResponseCompany>


    @DELETE("v1/publications/{publicationId}")
    suspend fun deletePublicationById(
        @Header("Authorization") token: String,
        @Path("publicationId") publicationId: Int
    ): Response<Unit>


//    @POST("v1/companies")
//    suspend fun createCompany(
//        @Body dto: CompanyWithoutIdDTO
//    ): Response<CompanyDTO>
//
//    @DELETE("v1/companies/{id}")
//    suspend fun deleteCompany(
//        @Path("id") id: Int
//    ): Response<Unit>
//
//    @GET("v1/companies/{id}/social-networks")
//    suspend fun getCompanyWithNetworks(
//        @Path("id") id: Int
//    ): Response<CompanyWithNetworksDTO>
//
//    @PUT("v1/companies/{id}")
//    suspend fun updateCompany(
//        @Path("id") id: Int,
//        @Body dto: CompanyWithoutIdDTO
//    ): Response<CompanyDTO>
}
