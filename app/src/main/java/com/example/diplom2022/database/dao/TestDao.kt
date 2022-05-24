package com.example.diplom2022.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.diplom2022.database.entities.Favorites
import com.example.diplom2022.database.entities.Question
import com.example.diplom2022.database.entities.Test

@Dao
interface TestDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTests(tests:List<Test>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertQuestions(questions:List<Question>)

    @Query("SELECT * from tests")
    fun getTestsList(): LiveData<List<Test>>

    @Query("SELECT * from tests where id = :id")
    suspend fun getTestById(id: Int)

    @Query("SELECT * from questions where test_id = :id")
    suspend fun getQuestionById(id: Int)
}