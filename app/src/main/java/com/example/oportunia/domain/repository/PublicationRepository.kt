package com.example.oportunia.domain.repository

import com.example.oportunia.domain.model.Publication

/**
 * This interface represents the PublicationRepository.
 */
interface PublicationRepository {
    suspend fun findAllPublications(): Result<List<Publication>>
    suspend fun findPublicationById(publicationId: Long): Result<Publication>
}