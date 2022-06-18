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
interface FavoriteDao {

    @Query("SELECT * from favorites where email = :email")
    fun getFavoritesList(email: String): LiveData<List<Favorite>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertFavorite(favorite: Favorite)

    @Query("DELETE FROM favorites where email = :email")
    fun deleteFavorite(email: String?)
}