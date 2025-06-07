package com.example.oportunia.data.remote.interceptor


import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import java.io.IOException
import javax.inject.Inject

/**
 * Interceptor to log and optionally modify HTTP responses.
 */class ResponseInterceptor @Inject constructor() : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        // 1) Toma la petición original
        val originalRequest = chain.request()
        val path = originalRequest.url.encodedPath  // p.ej. "/v1/users/email/1"

        // 2) Si es el endpoint de email, quítale cualquier Authorization
        val request = if (path.startsWith("/v1/users/email/")) {
            originalRequest.newBuilder()
                .removeHeader("Authorization")
                .build()
        } else {
            // en caso contrario, la dejas igual (o aquí podrías inyectar token)
            originalRequest
        }

        // 3) Procede con la petición limpia
        val response = chain.proceed(request)

        // 4) Reensambla el body para que puedas seguir leyendo
        val bodyString = response.body?.string().orEmpty()
        return response.newBuilder()
            .body(bodyString.toResponseBody(response.body?.contentType()))
            .build()
    }
}
