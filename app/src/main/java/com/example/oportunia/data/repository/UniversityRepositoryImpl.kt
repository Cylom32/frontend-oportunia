package com.example.oportunia.data.repository



import com.example.oportunia.data.datasource.UniversityDataSource
import com.example.oportunia.data.mapper.UniversityMapper
import com.example.oportunia.domain.model.University
import com.example.oportunia.domain.repository.UniversityRepository
import kotlinx.coroutines.flow.first

/**
 * Implementation of [UniversityRepository] that handles university data operations.
 * Maps between UniversityDTO and University domain model using [UniversityMapper].
 *
 * @property dataSource The data source for university operations.
 * @property mapper The mapper for converting between DTO and domain model.
 */
//class UniversityRepositoryImpl(
//    private val dataSource: UniversityDataSource,
//    private val mapper: UniversityMapper
//) : UniversityRepository {
//
//    override suspend fun findAllUniversities(): Result<List<University>> = runCatching {
//        dataSource.getUniversities().first().map { dto ->
//            mapper.mapToDomain(dto)
//        }
//    }
//
//
//}
