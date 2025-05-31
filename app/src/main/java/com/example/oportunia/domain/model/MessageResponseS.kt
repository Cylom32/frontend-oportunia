package com.example.oportunia.domain.model

data class MessageResponseS(
    val idMessage: Int,
    val detail: String,
    val file: String,
    val sendDate: String,
    val inbox: MessageInboxResponse
)