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
