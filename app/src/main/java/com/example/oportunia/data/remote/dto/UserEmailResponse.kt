package com.example.oportunia.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

data class UserEmailResponse(
    @SerializedName("id_user")
    val idUser: Int,
    @SerializedName("email")
    val email: String
)


//
//@JsonClass(generateAdapter = true)
//data class UserEmailResponse(
//    @Json(name = "id_user")
//    val idUser: Int,
//    val email: String
//)
//
