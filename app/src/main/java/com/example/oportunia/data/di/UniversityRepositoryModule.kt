package com.example.oportunia.data.di
import com.example.oportunia.data.remote.repository.UniversityRepositoryImpl
import com.example.oportunia.domain.repository.UniversityRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class UniversityRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindUniversityRepository(
        impl: UniversityRepositoryImpl
    ): UniversityRepository
}
