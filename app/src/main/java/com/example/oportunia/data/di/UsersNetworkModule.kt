package com.example.oportunia.data.di

import com.example.oportunia.data.remote.api.UsersService
import com.example.oportunia.data.remote.dto.UsersDTO
import com.example.oportunia.data.remote.interceptor.ResponseInterceptor
import com.example.oportunia.data.remote.serializer.UsersDeserializer
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UsersNetworkModule {
    private const val BASE_URL = "https://oportunia-zulu-app-v01-b4594a4da5e3.herokuapp.com/"


    private const val DATE_FORMAT = "yyyy-MM-dd"

    /**
     * Provides a singleton Gson instance configured with custom type adapters for Users.
     */
    @Provides
    @Singleton
    @Named("Users")
    fun provideUsersGson(): Gson = GsonBuilder()
        .registerTypeAdapter(UsersDTO::class.java, UsersDeserializer())
        .setDateFormat(DATE_FORMAT)
        .create()

    /**
     * Provides a logging interceptor for Users HTTP request/response logging.
     */
    @Provides
    @Singleton
    @Named("UsersLogging")
    fun provideUsersLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor()
        .apply { level = HttpLoggingInterceptor.Level.BODY }

    /**
     * Provides a configured OkHttpClient for Users.
     */
    @Provides
    @Singleton
    @Named("UsersClient")
    fun provideUsersOkHttpClient(
        @Named("UsersLogging") loggingInterceptor: HttpLoggingInterceptor,
        responseInterceptor: ResponseInterceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor(responseInterceptor)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()


    /////////////////////////////////////////






/////////////////////////////////////////////////



    /**
     * Provides a configured Retrofit instance for Users.
     */
    @Provides
    @Singleton
    @Named("Users")
    fun provideUsersRetrofit(
        @Named("UsersClient") okHttpClient: OkHttpClient,
        @Named("Users") gson: Gson
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(okHttpClient)
        .build()

    /**
     * Provides the UsersService implementation.
     */
    @Provides
    @Singleton
    fun provideUsersService(@Named("Users") retrofit: Retrofit): UsersService =
        retrofit.create(UsersService::class.java)
}







