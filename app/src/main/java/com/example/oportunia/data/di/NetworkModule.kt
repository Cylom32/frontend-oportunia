package com.example.oportunia.data.di



import com.example.oportunia.data.remote.api.AuthService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = "https://oportunia-zulu-app-v01-b4594a4da5e3.herokuapp.com/"
    private const val DATE_FORMAT = "yyyy-MM-dd"

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            // Aquí puedes añadir interceptores si los necesitas, por ejemplo:
            // .addInterceptor { chain ->
            //     val request = chain.request().newBuilder()
            //         .addHeader("Accept", "application/json")
            //         .build()
            //     chain.proceed(request)
            // }
            .build()

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideAuthService(retrofit: Retrofit): AuthService =
        retrofit.create(AuthService::class.java)
}
