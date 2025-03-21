package com.example.oportunia.domain.repository

import com.example.oportunia.domain.model.Location

/**
 * This interface represents the LocationRepository.
 */
interface LocationRepository {
    suspend fun findAllLocations(): Result<List<Location>>
    suspend fun findLocationById(locationId: Long): Result<Location>
}
