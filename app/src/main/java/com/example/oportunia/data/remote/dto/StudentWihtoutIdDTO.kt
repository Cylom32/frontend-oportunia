package com.example.oportunia.data.remote.dto

import com.google.gson.annotations.SerializedName
import java.time.LocalDate


data class StudentWihtoutIdDTO(
    @SerializedName("firstName")
    val firstName: String,

    @SerializedName("lastName1")
    val lastName1: String,

    @SerializedName("lastName2")
    val lastName2: String,

    @SerializedName("idUser")
    val idUser: Int,

    @SerializedName("idUniversity")
    val idUniversity: Int
)
