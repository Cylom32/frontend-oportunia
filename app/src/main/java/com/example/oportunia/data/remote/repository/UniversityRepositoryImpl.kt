package com.example.oportunia.data.remote.repository

import com.example.oportunia.data.remote.UniversitiesRemoteDataSource
import com.example.oportunia.data.mapper.UniversityMapper
import com.example.oportunia.domain.model.University
import com.example.oportunia.domain.repository.UniversityRepository
import javax.inject.Inject

class UniversityRepositoryImpl @Inject constructor(
    private val remoteDataSource: UniversitiesRemoteDataSource,
    private val mapper: UniversityMapper
) : UniversityRepository {

    override suspend fun findAllUniversities(): Result<List<University>> =
        remoteDataSource.getAllUniversities()
            .map { dtoList -> dtoList.map { mapper.mapToDomain(it) } }

    override suspend fun findUniversityById(
        token: String,
        universityId: Int
    ): Result<University> =
        remoteDataSource.getUniversityById(token, universityId)
            .map { mapper.mapToDomain(it) }

    override suspend fun saveUniversity(
        token: String,
        university: University
    ): Result<Unit> =
        remoteDataSource.createUniversity(token, mapper.mapToDto(university))
            .map { /* Unit */ }

    override suspend fun deleteUniversityById(
        token: String,
        universityId: Int
    ): Result<Unit> =
        remoteDataSource.deleteUniversity(token, universityId)
}
