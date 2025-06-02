package com.example.oportunia.domain.repository

import com.example.oportunia.data.remote.dto.StudentWihtoutIdDTO
import com.example.oportunia.domain.model.CVInput
import com.example.oportunia.domain.model.CVResponseS
import com.example.oportunia.domain.model.CvResponse
import com.example.oportunia.domain.model.Student
import com.example.oportunia.domain.model.StudentUpdateInput
import okhttp3.MultipartBody
import okhttp3.RequestBody

/**
 * This interface represents the StudentRepository.
 */
interface StudentRepository {
    suspend fun findAllStudents(): Result<List<Student>>
    suspend fun findStudentById(studentId: Int): Result<Student>

    //  suspend fun findStudentByIdUser(idUser: Int): Result<Student>
    suspend fun insertStudent(student: Student): Result<Unit>
    suspend fun updateStudent(student: Student): Result<Unit>
    suspend fun saveStudentNoId(dto: StudentWihtoutIdDTO): Result<Student>
    suspend fun findStudentByUserId(token: String, userId: Int): Result<Student>
    suspend fun findCvListByStudent(
        token: String,
        studentId: Int
    ): Result<List<CVResponseS>>


    suspend fun deleteCvById(token: String, cvId: Int): Result<Unit>

    suspend fun createCv(token: String, cvInput: CVInput): Result<Unit>

    suspend fun updateStudent(
        token: String,
        id: Int,
        studentUpdate: StudentUpdateInput
    ): Result<Unit>

    suspend fun uploadRemoteCv(
        filePart: MultipartBody.Part,
        namePart: RequestBody,
        idStudentPart: RequestBody
    ): Result<CvResponse>


}