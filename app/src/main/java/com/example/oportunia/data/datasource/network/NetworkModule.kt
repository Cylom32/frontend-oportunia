package com.example.oportunia.data.datasource.network

import com.example.oportunia.data.repository.RemoteUsersRepository
import com.example.oportunia.domain.model.Users
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
import javax.inject.Singleton

/**
 * Dagger module to provide network-related dependencies for Users.
 */@Module
@InstallIn(SingletonComponent::class)
object UserNetworkModule {

    @Provides
    @Singleton
    fun provideUserGson(): Gson {
        return GsonBuilder()
            .registerTypeAdapter(Users::class.java, UserDeserializer())
            .create()
    }

    @Provides
    @Singleton
    fun provideUserLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    fun provideResponseInterceptor(): ResponseInterceptor {
        return ResponseInterceptor()
    }

    @Provides
    @Singleton
    fun provideUserOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        responseInterceptor: ResponseInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(responseInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideUserRetrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://67e9d753bdcaa2b7f5ba4752.mockapi.io/api/v1/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideUserService(retrofit: Retrofit): UserService {
        return retrofit.create(UserService::class.java)
    }

    // 👉 Este es el nuevo método que inyecta el repo real
    @Provides
    @Singleton
    fun provideRemoteUsersRepository(userService: UserService): RemoteUsersRepository {
        return RemoteUsersRepository(userService)
    }
}
