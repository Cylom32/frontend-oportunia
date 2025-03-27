package com.example.oportunia.data.datasource

import com.example.oportunia.data.datasource.model.UniversityDTO
import com.example.oportunia.data.mapper.UniversityMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UniversityDataSourceImpl(
    private val universityMapper: UniversityMapper = UniversityMapper()
) : UniversityDataSource {

    override suspend fun getUniversities(): Flow<List<UniversityDTO>> = flow {
        val universities = UniversityProvider.findAllUniversities()
        emit(universities.map { universityMapper.mapToDto(it) })
    }

    override suspend fun getUniversityById(id: Int): UniversityDTO? {
        val university = UniversityProvider.findUniversityById(id)
        return university?.let { universityMapper.mapToDto(it) }
    }
}
