package com.example.oportunia.data.datasource.network



import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import java.io.IOException

/**
 * Interceptor to log and optionally modify HTTP responses.
 */
class ResponseInterceptor : Interceptor {

    /**
     * Intercepts the HTTP response to log and optionally modify it.
     *
     * @param chain The interceptor chain.
     * @return The intercepted and potentially modified response.
     * @throws IOException If an I/O error occurs during the interception.
     */
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        // 🔍 Mostramos la URL que se está llamando
        println(">> URL llamada por Retrofit: ${request.url}")

        val response = chain.proceed(request)
        val responseBodyString = response.body?.string()

        println("Raw Response: $responseBodyString")

        return response.newBuilder()
            .body((responseBodyString ?: "").toResponseBody(response.body?.contentType()))
            .build()
    }



}