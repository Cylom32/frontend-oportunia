package com.example.oportunia.data.di


import com.example.oportunia.data.remote.api.StudentsService
import com.example.oportunia.data.remote.interceptor.ResponseInterceptor
import com.example.oportunia.data.remote.dto.StudentDTO
import com.example.oportunia.data.remote.serializer.LocalDateAdapter
import com.example.oportunia.data.remote.serializer.StudentDeserializer
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDate
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object StudentsNetworkModule {
    private const val BASE_URL = "https://oportunia-zulu-app-v01-b4594a4da5e3.herokuapp.com/"
    private const val DATE_FORMAT = "yyyy-MM-dd"

    /** Gson especializado para StudentDTO */
    @Provides
    @Singleton
    @Named("Students")
    fun provideStudentsGson(): Gson = GsonBuilder()
        .registerTypeAdapter(StudentDTO::class.java, StudentDeserializer())
        .registerTypeAdapter(LocalDate::class.java, LocalDateAdapter())
        .setDateFormat(DATE_FORMAT)
        .create()

    /** Logging interceptor para estudiantes */
    @Provides
    @Singleton
    @Named("StudentsLogging")
    fun provideStudentsLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

    /** Cliente OkHttp para estudiantes */
    @Provides
    @Singleton
    @Named("StudentsClient")
    fun provideStudentsOkHttpClient(
        @Named("StudentsLogging") logging: HttpLoggingInterceptor,
        responseInterceptor: ResponseInterceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(logging)
        .addInterceptor(responseInterceptor)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    /** Retrofit específico para estudiantes */
    @Provides
    @Singleton
    @Named("Students")
    fun provideStudentsRetrofit(
        @Named("StudentsClient") client: OkHttpClient,
        @Named("Students") gson: Gson
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    /** Servicio Retrofit para llamadas a /v1/students */
    @Provides
    @Singleton
    fun provideStudentsService(@Named("Students") retrofit: Retrofit): StudentsService =
        retrofit.create(StudentsService::class.java)
}
