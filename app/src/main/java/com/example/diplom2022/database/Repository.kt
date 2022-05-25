package com.example.diplom2022.database

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.diplom2022.database.dao.LessonDao
import com.example.diplom2022.database.dao.TestDao
import com.example.diplom2022.database.entities.Favorite
import com.example.diplom2022.database.entities.Lesson
import com.example.diplom2022.database.entities.Question
import com.example.diplom2022.database.entities.Test
import kotlinx.coroutines.flow.Flow

class Repository(private val lessonDao: LessonDao, private val testDao: TestDao) {

    val lessons: LiveData<List<Lesson>> = lessonDao.getLessonsList()

    val tests: LiveData<List<Test>> = testDao.getTestsList()

    val questions: LiveData<List<Question>> = testDao.getQuestionsList()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertFavorite(favorite: Favorite) {
        lessonDao.insertFavourite(favorite)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteFavorite(email : String) {
        lessonDao.deleteFavorite(email)
    }

}
