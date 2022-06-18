package com.example.diplom2022.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.diplom2022.database.entities.Favorite
import com.example.diplom2022.database.entities.Lesson
import kotlinx.coroutines.flow.Flow

@Dao
interface LessonDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertLessons(list: List<Lesson>)

    @Query("SELECT * from lessons")
    fun getLessonsList(): LiveData<List<Lesson>>

    @Query("DELETE FROM favorites where email = :email")
    fun deleteFavorite(email: String)
}