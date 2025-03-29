package com.example.oportunia.domain.repository

import com.example.oportunia.domain.model.Cv

/**
 * This interface represents the CvRepository.
 */
interface CvRepository {
    suspend fun findAllCvs(): Result<List<Cv>>
    suspend fun findCvById(cvId: Int): Result<Cv>
    suspend fun findCvByStudentId(studentId: Int): Result<Cv>
    suspend fun findAllCvByStudentId(studentId: Int): Result<List<Cv>>
    suspend fun insertCv(cv: Cv): Result<Unit>
    suspend fun updateCv(cv: Cv): Result<Unit>
    suspend fun deleteCv(cv: Cv): Result<Unit>
    suspend fun changeStatusById(cvId: Int): Result<Unit>
}
