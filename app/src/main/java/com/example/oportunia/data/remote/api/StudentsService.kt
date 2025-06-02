package com.example.oportunia.data.remote.api

import com.example.oportunia.data.remote.dto.StudentDTO
import com.example.oportunia.data.remote.dto.StudentWihtoutIdDTO
import com.example.oportunia.domain.model.CVInput
import com.example.oportunia.domain.model.CVResponseS
import com.example.oportunia.domain.model.CvResponse
import com.example.oportunia.domain.model.StudentUpdateInput
import okhttp3.MultipartBody
import okhttp3.RequestBody
//import com.example.oportunia.data.remote.dto.StudentWithoutIdDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

interface StudentsService {

    /** GET /v1/students */
    @GET("v1/students")
    suspend fun getAllStudents(): Response<List<StudentDTO>>

    /** GET /v1/students/{id} */
    @GET("v1/students/{id}")
    suspend fun getStudentById(@Path("id") id: Int): Response<StudentDTO>

    /** POST /v1/students */
    @POST("v1/students")
    suspend fun createStudent(@Body student: StudentDTO): Response<StudentDTO>

    @POST("v1/students")
    suspend fun createStudent(
        @Body student: StudentWihtoutIdDTO
    ): Response<StudentDTO>

    /** DELETE /v1/students/{id} */
    @DELETE("v1/students/{id}")
    suspend fun deleteStudent(@Path("id") id: Int): Response<Unit>

    /** PUT /v1/students/{id} */
    @PUT("v1/students/{id}")
    suspend fun updateStudent(
        @Path("id") id: Int,
        @Body student: StudentDTO
    ): Response<StudentDTO>

    @GET("v1/students/user/{userId}")
    suspend fun getStudentByUserId(
        @Header("Authorization") token: String,
        @Path("userId") userId: Int
    ): Response<StudentDTO>

    @GET("v1/cvs/students/{student_id}")
    suspend fun getCvListByStudent(
        @Header("Authorization") token: String,
        @Path("student_id") studentId: Int
    ): Response<List<CVResponseS>>

    @DELETE("v1/cvs/{cv_id}")
    suspend fun deleteCvById(
        @Header("Authorization") token: String,
        @Path("cv_id") cvId: Int
    ): Response<Unit>

    @POST("v1/cvs")
    suspend fun createCv(
        @Header("Authorization") token: String,
        @Body cvInput: CVInput
    ): Response<Unit>

    @PUT("v1/students/{id}")
    suspend fun updateStudent(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
        @Body student: StudentUpdateInput
    ): Response<Unit>


    @Multipart
    @POST("v1/cvs/remote")
    suspend fun uploadRemoteCv(
        @Part file: MultipartBody.Part,
        @Part("name") name: RequestBody,
        @Part("idStudent") idStudent: RequestBody
    ): Response<CvResponse>




}
