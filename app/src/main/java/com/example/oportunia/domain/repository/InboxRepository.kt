package com.example.oportunia.domain.repository

import com.example.oportunia.domain.model.Inbox

/**
 * This interface represents the InboxRepository.
 */


interface InboxRepository {
    suspend fun findAllInboxes(): Result<List<Inbox>>
    suspend fun findInboxById(inboxId: Long): Result<Inbox>
}
