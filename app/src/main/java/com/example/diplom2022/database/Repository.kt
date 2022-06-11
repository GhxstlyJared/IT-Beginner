package com.example.diplom2022.database

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.diplom2022.database.dao.FavoriteDao
import com.example.diplom2022.database.dao.LessonDao
import com.example.diplom2022.database.dao.TestDao
import com.example.diplom2022.database.entities.Favorite
import com.example.diplom2022.database.entities.Lesson
import com.example.diplom2022.database.entities.Question
import com.example.diplom2022.database.entities.Test

class Repository(lessonDao: LessonDao, testDao: TestDao, private val favoriteDo: FavoriteDao) {

    val lessons: LiveData<List<Lesson>> = lessonDao.getLessonsList()

    val tests: LiveData<List<Test>> = testDao.getTestsList()

    val questions: LiveData<List<Question>> = testDao.getQuestionsList()

    fun getFavorites(email: String) : LiveData<List<Favorite>> {
        return favoriteDo.getFavoritesList(email)
    }

    fun insertFavorite(favorite: Favorite) {
       favoriteDo.insertFavorite(favorite)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteFavorite(email : String?) {
        favoriteDo.deleteFavorite(email)
    }

}
