package com.example.oportunia.data.datasource

import com.example.oportunia.data.datasource.model.UniversityDTO
import kotlinx.coroutines.flow.Flow

interface UniversityDataSource {
    suspend fun getUniversities(): Flow<List<UniversityDTO>>
    suspend fun getUniversityById(id: Int): UniversityDTO?
}
