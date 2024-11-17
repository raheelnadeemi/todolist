package com.example.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.bean.EntityBean


@Dao
interface Dao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: EntityBean)

    @Query("SELECT * from todo")
    suspend fun fetch(): List<EntityBean>
}