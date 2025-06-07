package com.example.oportunia.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CVListResponse(
    @SerializedName("idCv") val idCv: Int,
    @SerializedName("name") val name: String,
    @SerializedName("file") val file: String
)