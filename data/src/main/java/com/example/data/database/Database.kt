package com.example.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.bean.EntityBean


@Database(entities = [EntityBean::class], version = 1)
abstract class Database: RoomDatabase() {
    abstract fun getDao(): Dao
}