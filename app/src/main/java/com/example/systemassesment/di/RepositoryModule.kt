package com.example.systemassesment.di

import com.example.systemassesment.data.repository.AuthRepository
import com.example.systemassesment.data.repository.AuthRepositoryImpl
import com.example.systemassesment.data.repository.PostRepository
import com.example.systemassesment.data.repository.PostRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindPostRepository(impl: PostRepositoryImpl): PostRepository

    @Binds
    @Singleton
    abstract fun bindAuthRepository(impl: AuthRepositoryImpl): AuthRepository
}
