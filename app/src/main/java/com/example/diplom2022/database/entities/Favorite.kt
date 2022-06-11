package com.example.diplom2022.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class Favorite(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "lesson_id") var lessonId: Int? = null,
    @ColumnInfo(name = "email") var email: String? = null
)
