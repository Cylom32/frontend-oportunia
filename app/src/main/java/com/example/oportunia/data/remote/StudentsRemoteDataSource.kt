package com.example.oportunia.data.remote

import com.example.oportunia.data.remote.api.StudentsService
import com.example.oportunia.data.remote.dto.StudentDTO
import com.example.oportunia.data.remote.dto.StudentWihtoutIdDTO
import com.example.oportunia.domain.model.CVInput
import com.example.oportunia.domain.model.CVResponseS
import retrofit2.Response
import javax.inject.Inject

class StudentsRemoteDataSource @Inject constructor(
    private val service: StudentsService
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

    /** GET /v1/students */
    suspend fun getAllStudents(): Result<List<StudentDTO>> =
        safeApiCall { service.getAllStudents() }

    /** GET /v1/students/{id} */
    suspend fun getStudentById(id: Int): Result<StudentDTO> =
        safeApiCall { service.getStudentById(id) }

    /** POST /v1/students con DTO que incluye id (para casos de update o test) */
    suspend fun createStudent(dto: StudentDTO): Result<StudentDTO> =
        safeApiCall { service.createStudent(dto) }

    /** POST /v1/students con DTO sin id —la BD lo genera— */
    suspend fun createStudent(dto: StudentWihtoutIdDTO): Result<StudentDTO> =
        safeApiCall { service.createStudent(dto) }

    /** DELETE /v1/students/{id} */
    suspend fun deleteStudent(id: Int): Result<Unit> =
        safeApiCall { service.deleteStudent(id) }

    /** PUT /v1/students/{id} */
    suspend fun updateStudent(id: Int, dto: StudentDTO): Result<StudentDTO> =
        safeApiCall { service.updateStudent(id, dto) }

    /** GET /v1/students/user/{userId} */
    suspend fun getStudentByUserId(token: String, userId: Int): Result<StudentDTO> =
        safeApiCall { service.getStudentByUserId(token, userId) }


    suspend fun findCvListByStudent(
        token: String,
        studentId: Int
    ): Result<List<CVResponseS>> =
        safeApiCall { service.getCvListByStudent(token, studentId) }

    suspend fun deleteCvById(token: String, cvId: Int): Result<Unit> =
        safeApiCall { service.deleteCvById(token, cvId) }

    suspend fun createCv(token: String, cvInput: CVInput): Result<Unit> =
        safeApiCall { service.createCv(token, cvInput) }
}
