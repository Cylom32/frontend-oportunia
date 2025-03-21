package com.example.oportunia.domain.repository

import com.example.oportunia.domain.model.Message

/**
 * This interface represents the MessageRepository.
 */
interface MessageRepository {
    suspend fun findAllMessages(): Result<List<Message>>
    suspend fun findMessageById(messageId: Long): Result<Message>
}