package com.example.diplom2022.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tests")
data class Test(
    @PrimaryKey(autoGenerate = true) val id:Int,
    @ColumnInfo(name = "name") val title: String,
)
