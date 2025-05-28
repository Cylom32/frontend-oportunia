// src/main/kotlin/com/example/oportunia/domain/repository/UniversityRepository.kt

package com.example.oportunia.domain.repository

import com.example.oportunia.domain.model.University

interface UniversityRepository {
    suspend fun findAllUniversities(): Result<List<University>>
    suspend fun findUniversityById(token: String, universityId: Int): Result<University>
    suspend fun saveUniversity(token: String, university: University): Result<Unit>
    suspend fun deleteUniversityById(token: String, universityId: Int): Result<Unit>
}
