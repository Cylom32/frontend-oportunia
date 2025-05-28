package com.example.oportunia.data.di


import com.example.oportunia.data.remote.api.CompaniesService
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
object CompaniesNetworkModule {
    private const val BASE_URL = "https://oportunia-zulu-app-v01-b4594a4da5e3.herokuapp.com/"
    private const val DATE_FORMAT = "yyyy-MM-dd"

    /** Provides a singleton Gson for Companies. */
    @Provides
    @Singleton
    @Named("Companies")
    fun provideCompaniesGson(): Gson = GsonBuilder()
        .setDateFormat(DATE_FORMAT)
        .create()

    /** Provides HTTP logging for Companies. */
    @Provides
    @Singleton
    @Named("CompaniesLogging")
    fun provideCompaniesLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

    /** Provides OkHttpClient for Companies. */
    @Provides
    @Singleton
    @Named("CompaniesClient")
    fun provideCompaniesOkHttpClient(
        @Named("CompaniesLogging") logging: HttpLoggingInterceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(logging)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    /** Provides Retrofit for Companies. */
    @Provides
    @Singleton
    @Named("Companies")
    fun provideCompaniesRetrofit(
        @Named("CompaniesClient") client: OkHttpClient,
        @Named("Companies") gson: Gson
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    /** Exposes the CompaniesService. */
    @Provides
    @Singleton
    fun provideCompaniesService(
        @Named("Companies") retrofit: Retrofit
    ): CompaniesService = retrofit.create(CompaniesService::class.java)
}
