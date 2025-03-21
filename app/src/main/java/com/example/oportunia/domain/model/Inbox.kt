package com.example.oportunia.domain.model

/**
 * Represents an inbox for messages.
 * @property id The unique identifier of the inbox.
 * @property companyId The company associated with the inbox.
 */
data class Inbox(
    val id: Int,
    val companyId: Int
)

