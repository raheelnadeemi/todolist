package com.example.data.di

import com.example.data.transformation.Transformer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@InstallIn(SingletonComponent::class)
@Module
object TransformationModule {

    @Provides
    fun provideTransformer(): Transformer =
        Transformer()
}