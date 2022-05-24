package com.example.diplom2022.database.entities

import androidx.room.Embedded
import androidx.room.Relation

data class AnswersAndQuesion(
    @Embedded val answers: ,
    @Relation(
        parentColumn = "userId",
        entityColumn = "userOwnerId"
    )
    val library: Library
)