// src/main/kotlin/com/example/oportunia/data/remote/api/UniversitiesService.kt

package com.example.oportunia.data.remote.api

import com.example.oportunia.data.remote.dto.UniversityDTO
import retrofit2.Response
import retrofit2.http.*

interface UniversitiesService {

    @GET("v1/universities")
    suspend fun getAllUniversities(
    ): Response<List<UniversityDTO>>

    @GET("v1/universities/{id}")
    suspend fun getUniversityById(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Response<UniversityDTO>

    @POST("v1/universities")
    suspend fun createUniversity(
        @Header("Authorization") token: String,
        @Body dto: UniversityDTO
    ): Response<UniversityDTO>

    @DELETE("v1/universities/{id}")
    suspend fun deleteUniversity(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Response<Unit>
}
