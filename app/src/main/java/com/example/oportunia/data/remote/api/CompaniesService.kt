package com.example.oportunia.data.remote.api

import com.example.oportunia.data.remote.dto.*
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
