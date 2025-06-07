// CloudinaryService.kt
package com.example.oportunia.presentation.ui.cloudinary

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.json.JSONObject
import java.io.File

class CloudinaryService(
    private val cloudName: String,
    private val uploadPreset: String
) {
    private val client = OkHttpClient()

    // Método asíncrono que NO bloquea el UI thread
    suspend fun uploadImage(file: File): String? = withContext(Dispatchers.IO) {
        try {
            val url = "https://api.cloudinary.com/v1_1/$cloudName/image/upload"

            val requestBody: RequestBody = MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("upload_preset", uploadPreset)
                .addFormDataPart(
                    "file", file.name,
                    file.asRequestBody("image/*".toMediaTypeOrNull())
                )
                .build()

            val request: Request = Request.Builder()
                .url(url)
                .post(requestBody)
                .build()

            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) return@withContext null
                val body = response.body?.string() ?: return@withContext null
                val json = JSONObject(body)
                return@withContext json.getString("secure_url")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return@withContext null
        }
    }



    suspend fun uploadPdf(file: File): String? = withContext(Dispatchers.IO) {
        try {
            val requestBody: RequestBody = MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("upload_preset", uploadPreset)
                .addFormDataPart(
                    "file", file.name,
                    file.asRequestBody("application/pdf".toMediaTypeOrNull())
                )
                .build()

            val request: Request = Request.Builder()
                .url("https://api.cloudinary.com/v1_1/$cloudName/raw/upload") // <- usar /raw/ para PDF
                .post(requestBody)
                .build()

            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) return@withContext null
                val body = response.body?.string() ?: return@withContext null
                val json = JSONObject(body)
                return@withContext json.getString("secure_url")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return@withContext null
        }
    }


}