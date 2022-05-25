package com.example.diplom2022.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "questions")
data class Question(
    @PrimaryKey(autoGenerate = true) val id:Int,
    @ColumnInfo(name = "test_id") val testId: Int,
    @ColumnInfo(name = "question") val title: String,
    @ColumnInfo(name = "answer_A") val answerA: String,
    @ColumnInfo(name = "answer_B") val answerB: String,
    @ColumnInfo(name = "answer_C") val answerC: String,
    @ColumnInfo(name = "correct_answer") val correctAnswer: Int //    range 1..3
                                                                //    answer_A - 1
                                                                //    answer_B - 2
                                                                //    answer_C - 3
)
