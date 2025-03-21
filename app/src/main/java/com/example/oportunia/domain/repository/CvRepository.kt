package com.example.oportunia.domain.repository

import com.example.oportunia.domain.model.Cv

/**
 * This interface represents the CvRepository.
 */
interface CvRepository {
    suspend fun findAllCvs(): Result<List<Cv>>
    suspend fun findCvById(cvId: Long): Result<Cv>
}