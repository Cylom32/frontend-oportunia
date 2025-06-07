package com.example.oportunia.data.di

import com.example.oportunia.data.remote.api.UniversitiesService
import com.example.oportunia.data.remote.interceptor.ResponseInterceptor
import com.example.oportunia.data.remote.dto.UniversityDTO
import com.example.oportunia.data.remote.serializer.UniversityDeserializer
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
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UniversitiesNetworkModule {
    private const val BASE_URL = "https://oportunia-zulu-app-v01-b4594a4da5e3.herokuapp.com/"
    private const val DATE_FORMAT = "yyyy-MM-dd"

    /** Gson especializado para UniversityDTO */
    @Provides
    @Singleton
    @Named("Universities")
    fun provideUniversitiesGson(): Gson = GsonBuilder()
        .registerTypeAdapter(UniversityDTO::class.java, UniversityDeserializer())
        .setDateFormat(DATE_FORMAT)
        .create()

    /** Logging interceptor para universidades */
    @Provides
    @Singleton
    @Named("UniversitiesLogging")
    fun provideUniversitiesLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

    /** Cliente OkHttp para universidades */
    @Provides
    @Singleton
    @Named("UniversitiesClient")
    fun provideUniversitiesOkHttpClient(
        @Named("UniversitiesLogging") logging: HttpLoggingInterceptor,
        responseInterceptor: ResponseInterceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(logging)
        .addInterceptor(responseInterceptor)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    /** Retrofit específico para universidades */
    @Provides
    @Singleton
    @Named("Universities")
    fun provideUniversitiesRetrofit(
        @Named("UniversitiesClient") client: OkHttpClient,
        @Named("Universities") gson: Gson
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    /** Servicio Retrofit para llamadas a /v1/universities */
    @Provides
    @Singleton
    fun provideUniversitiesService(@Named("Universities") retrofit: Retrofit): UniversitiesService =
        retrofit.create(UniversitiesService::class.java)
}
