package com.example.oportunia.domain.model

data class CvResponse(
    val idStudent: Int,
    val name: String,
    val extractedText: String,
    val analysis: String
)
