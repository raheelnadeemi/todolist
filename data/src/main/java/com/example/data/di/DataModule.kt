package com.example.data.di

import android.content.Context
import androidx.room.Room
import com.example.data.database.Dao
import com.example.data.database.Database
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object DataModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): Database =
        Room.databaseBuilder(context.applicationContext, Database::class.java,"TodoDatabase").build()

    @Provides
    fun provideDao(database: Database): Dao {
        return database.getDao()
    }


}