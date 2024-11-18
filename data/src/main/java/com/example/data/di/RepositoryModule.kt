package com.example.data.di

import com.example.data.database.Dao
import com.example.data.repository.RepositoryImpl
import com.example.data.transformation.Transformer
import com.example.domain.repository.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Provides
    fun provideRepository(dao: Dao, transformer: Transformer): Repository {
        return RepositoryImpl(dao, transformer)
    }
}