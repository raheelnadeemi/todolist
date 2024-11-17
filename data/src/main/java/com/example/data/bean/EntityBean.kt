package com.example.data.bean

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo")
data class EntityBean(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    @ColumnInfo(name = "text") val item: String
)

