package com.example.oportunia.domain.model

import java.util.Date

/**
 * Represents a message.
 * @property id The unique identifier of the message.
 * @property detail The content of the message.
 * @property file The file attachment of the message.
 * @property sendDate The date when the message was sent.
 * @property userId The sender user ID.
 * @property inboxId The inbox where the message belongs.
 */
data class Message(
    val id: Long,
    val detail: String,
    val file: String?,
    val sendDate: Date,
    val userId: Int,
    val inboxId: Int
)