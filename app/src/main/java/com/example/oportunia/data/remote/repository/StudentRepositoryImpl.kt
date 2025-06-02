package com.example.oportunia.data.remote.repository

import com.example.oportunia.data.mapper.StudentMapper
import com.example.oportunia.data.remote.StudentsRemoteDataSource
import com.example.oportunia.data.remote.dto.StudentWihtoutIdDTO
import com.example.oportunia.domain.model.CVInput
import com.example.oportunia.domain.model.CVResponseS
import com.example.oportunia.domain.model.CvResponse
import com.example.oportunia.domain.model.Student
import com.example.oportunia.domain.model.StudentUpdateInput
import com.example.oportunia.domain.repository.StudentRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.net.UnknownHostException
import javax.inject.Inject

class StudentRepositoryImpl @Inject constructor(
    private val remoteDataSource: StudentsRemoteDataSource,
    private val studentMapper: StudentMapper
) : StudentRepository {

    override suspend fun findAllStudents(): Result<List<Student>> {
        return try {
            remoteDataSource.getAllStudents().map { dtoList ->
                dtoList.map { studentMapper.mapToDomain(it) }
            }
        } catch (e: UnknownHostException) {
            Result.failure(Exception("Network error: unable to connect to server."))
        } catch (e: Exception) {
            Result.failure(Exception("Error fetching students: ${e.message}"))
        }
    }

    override suspend fun findStudentById(studentId: Int): Result<Student> {
        return try {
            remoteDataSource.getStudentById(studentId).map { dto ->
                studentMapper.mapToDomain(dto)
            }
        } catch (e: Exception) {
            Result.failure(Exception("Error fetching student with id=$studentId: ${e.message}"))
        }
    }

//    override suspend fun findStudentByIdUser(idUser: Int): Result<Student> {
//        return try {
//            remoteDataSource.getStudentByUserId(idUser).map { dto ->
//                studentMapper.mapToDomain(dto)
//            }
//        } catch (e: Exception) {
//            Result.failure(Exception("Error fetching student for user id=$idUser: ${e.message}"))
//        }
//    }

    override suspend fun insertStudent(student: Student): Result<Unit> {
        return try {
            val dto = studentMapper.mapToDto(student)
            remoteDataSource.createStudent(dto)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(Exception("Error creating student: ${e.message}"))
        }
    }

    override suspend fun updateStudent(student: Student): Result<Unit> {
        return try {
            val dto = studentMapper.mapToDto(student)
            remoteDataSource.updateStudent(student.idStudent ?: throw IllegalArgumentException("Missing student ID"), dto)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(Exception("Error updating student id=${student.idStudent}: ${e.message}"))
        }
    }

    override suspend fun saveStudentNoId(dtoInput: StudentWihtoutIdDTO): Result<Student> = try {
        remoteDataSource.createStudent(dtoInput)
            .map { createdDto ->
                studentMapper.mapToDomain(createdDto)
            }
    } catch (e: Exception) {
        Result.failure(Exception("Error creating student without ID: ${e.message}"))
    }



    override suspend fun findStudentByUserId(token: String, userId: Int): Result<Student> =
        remoteDataSource
            .getStudentByUserId(token, userId)
            .map { dto -> studentMapper.mapToDomain(dto) }

    override suspend fun findCvListByStudent(
        token: String,
        studentId: Int
    ): Result<List<CVResponseS>> = try {
        remoteDataSource.findCvListByStudent(token, studentId)
    } catch (e: Exception) {
        Result.failure(Exception("Error fetching CV list for student id=$studentId: ${e.message}"))
    }



    override suspend fun deleteCvById(token: String, cvId: Int): Result<Unit> =
        remoteDataSource.deleteCvById(token, cvId)


    override suspend fun createCv(token: String, cvInput: CVInput): Result<Unit> = try {
        remoteDataSource.createCv(token, cvInput)
    } catch (e: Exception) {
        Result.failure(Exception("Error uploading CV: ${e.message}"))
    }

    override suspend fun updateStudent(
        token: String,
        id: Int,
        studentUpdate: StudentUpdateInput
    ): Result<Unit> =
        try {
            remoteDataSource.updateStudent(token, id, studentUpdate)
        } catch (e: Exception) {
            Result.failure(Exception("Error updating student id=$id: ${e.message}"))
        }

    override suspend fun uploadRemoteCv(
        filePart: MultipartBody.Part,
        namePart: RequestBody,
        idStudentPart: RequestBody
    ): Result<CvResponse> =
        try {
            remoteDataSource.uploadRemoteCv(filePart, namePart, idStudentPart)
        } catch (e: Exception) {
            Result.failure(Exception("Error uploading CV: ${e.message}"))
        }





}
