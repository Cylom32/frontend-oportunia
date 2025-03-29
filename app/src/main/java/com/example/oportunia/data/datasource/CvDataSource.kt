package com.example.oportunia.data.datasource

import com.example.oportunia.data.datasource.model.CvDTO
import kotlinx.coroutines.flow.Flow

interface CvDataSource {
    suspend fun getCvs(): Flow<List<CvDTO>>
    suspend fun getCvById(id: Int): CvDTO?
    suspend fun insertCv(cvDTO: CvDTO)
    suspend fun updateCv(cvDTO: CvDTO)
    suspend fun deleteCv(cvDTO: CvDTO)
    suspend fun findCvByStudentId(studentId: Int): CvDTO?
    suspend fun getCvByStudentId(studentId: Int): CvDTO?
    suspend fun findAllCvByStudentId(studentId: Int): Flow<List<CvDTO>>
    suspend fun changeStatusById(cvId: Int)


}
