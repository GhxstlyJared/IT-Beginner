package com.example.diplom2022.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "lessons")
data class Lesson (
    @PrimaryKey(autoGenerate = true) val id:Int,
    @ColumnInfo(name = "name") val title: String,
    @ColumnInfo(name = "content") val content : String
)
