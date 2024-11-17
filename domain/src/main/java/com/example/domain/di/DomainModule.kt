package com.example.domain.di

import com.example.domain.repository.Repository
import com.example.domain.transformation.Transformer
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
    fun provideInsertUseCase(repository: Repository, transformer: Transformer): InsertUseCase {
        return InsertUseCase(repository, transformer)
    }

    @Provides
    fun provideFetchUseCase(repository: Repository, transformer: Transformer): FetchUseCase {
        return FetchUseCase(repository,transformer)
    }


    @Provides
    fun provideTransformer(): Transformer =
        Transformer()
}