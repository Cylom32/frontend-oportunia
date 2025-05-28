package com.example.oportunia.data.di

import com.example.oportunia.data.remote.repository.StudentRepositoryImpl
import com.example.oportunia.domain.repository.StudentRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class StudentsRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindStudentsRepository(
        impl: StudentRepositoryImpl
    ): StudentRepository
}
