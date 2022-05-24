package com.example.diplom2022.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.diplom2022.database.entities.Favorites

import com.example.diplom2022.database.entities.Lesson

@Dao
interface LessonDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertLessons(list: List<Lesson>)

    @Query("SELECT * from lessons")
    fun getLessonsList(): LiveData<List<Lesson>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavourite(favorites: Favorites)

    @Query("DELETE FROM favorites where email = :favorites")
    suspend fun deleteFavorite(favorites: Favorites)
}