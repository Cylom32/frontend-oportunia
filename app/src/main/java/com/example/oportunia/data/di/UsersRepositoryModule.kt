package com.example.oportunia.data.di

//import com.example.oportunia.data.repository.UsersRepositoryImpl
import com.example.oportunia.data.remote.repository.UsersRepositoryImpl
import com.example.oportunia.domain.repository.UsersRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class UsersRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindUsersRepository(
        //impl: UsersRepositoryImpl
        impl: UsersRepositoryImpl
    ): UsersRepository
}
