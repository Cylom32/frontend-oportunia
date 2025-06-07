package com.example.oportunia.domain.model

import com.example.oportunia.data.remote.dto.InboxxResponse

data class MessageResponseC(
    val idMessage: Int,
    val detail: String,
    val file: String,
    val sendDate: String,
    val inbox: InboxxResponse
)