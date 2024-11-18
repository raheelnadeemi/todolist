package com.example.domain.di

import android.content.Context
import com.example.domain.repository.Repository
import com.example.domain.usecase.FetchUseCase
import com.example.domain.usecase.InsertUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@InstallIn(SingletonComponent::class)
@Module
object DomainModule {

    @Provides
    fun provideInsertUseCase(repository: Repository, context: Context): InsertUseCase {
        return InsertUseCase(repository, context)
    }

    @Provides
    fun provideFetchUseCase(repository: Repository, context: Context): FetchUseCase {
        return FetchUseCase(repository, context)
    }



}