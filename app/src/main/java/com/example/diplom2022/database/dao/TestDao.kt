package com.example.diplom2022.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.diplom2022.database.entities.Question
import com.example.diplom2022.database.entities.Test
import kotlinx.coroutines.flow.Flow

@Dao
interface TestDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertTests(tests:List<Test>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertQuestions(questions:List<Question>)

    @Query("SELECT * from tests")
    fun getTestsList(): LiveData<List<Test>>

    @Query("SELECT * from questions")
    fun getQuestionsList():LiveData<List<Question>>
}