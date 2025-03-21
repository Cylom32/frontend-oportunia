package com.example.oportunia.domain.repository

import com.example.oportunia.domain.model.Area

/**
 * This interface represents the AreaRepository.
 */
interface AreaRepository {
    suspend fun findAllAreas(): Result<List<Area>>
    suspend fun findAreaById(areaId: Long): Result<Area>
}
