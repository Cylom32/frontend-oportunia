package com.example.oportunia.domain.model

data class MessageInput(
    val detail: String,
    val file: String,
    val idInbox: Int,
    val idStudent: Int
)