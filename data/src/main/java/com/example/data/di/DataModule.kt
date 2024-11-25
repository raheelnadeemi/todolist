package com.example.data.di

import android.content.Context
import androidx.room.Room
import com.example.data.database.TodoDao
import com.example.data.database.TodoDatabase
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
    fun provideDatabase(@ApplicationContext context: Context): TodoDatabase =
        Room.databaseBuilder(context.applicationContext, TodoDatabase::class.java,"TodoDatabase").build()

    @Provides
    fun provideDao(database: TodoDatabase): TodoDao {
        return database.getDao()
    }


}