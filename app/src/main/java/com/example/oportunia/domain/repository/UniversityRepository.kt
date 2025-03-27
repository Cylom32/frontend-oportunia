package com.example.oportunia.domain.repository

import com.example.oportunia.domain.model.University

/**
 * This interface represents the UniversityRepository.
 */
interface UniversityRepository {
    suspend fun findAllUniversities(): Result<List<University>>
    suspend fun findUniversityById(universityId: Int): Result<University>
}