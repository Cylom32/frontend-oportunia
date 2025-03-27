package com.example.oportunia.data.datasource.model

import com.example.oportunia.data.datasource.UniversityDataSource
import com.example.oportunia.data.mapper.UniversityMapper
import com.example.oportunia.domain.model.University
import com.example.oportunia.domain.repository.UniversityRepository
import kotlinx.coroutines.flow.first

class UniversityRepositoryImpl(
    private val dataSource: UniversityDataSource,
    private val mapper: UniversityMapper
) : UniversityRepository {

    override suspend fun findAllUniversities(): Result<List<University>> = runCatching {
        dataSource.getUniversities().first().map { dto ->
            mapper.mapToDomain(dto)
        }
    }

    override suspend fun findUniversityById(id: Int): Result<University> = runCatching {
        val dto = dataSource.getUniversityById(id) ?: error("University not found")
        mapper.mapToDomain(dto)
    }
}
