package com.example.oportunia.data.di

import com.example.oportunia.data.remote.repository.CompaniesRepositoryImpl
import com.example.oportunia.domain.repository.CompanyRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CompaniesRepositoryModule {
    @Binds
    @Singleton
    abstract fun bindCompaniesRepository(
        impl: CompaniesRepositoryImpl
    ): CompanyRepository
}
