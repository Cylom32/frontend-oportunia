package com.example.oportunia.domain.repository

import com.example.oportunia.domain.model.SocialNetwork

/**
 * This interface represents the SocialNetworkRepository.
 */
interface SocialNetworkRepository {
    suspend fun findAllSocialNetworks(): Result<List<SocialNetwork>>
    suspend fun findSocialNetworkById(socialNetworkId: Long): Result<SocialNetwork>
}
