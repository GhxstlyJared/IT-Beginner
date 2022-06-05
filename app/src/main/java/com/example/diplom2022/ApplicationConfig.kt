package com.example.diplom2022

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.diplom2022.database.AppRoomDatabase
import com.example.diplom2022.database.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class ApplicationConfig : Application() {
    val database by lazy { AppRoomDatabase.getInstance(this) }
    val repository by lazy { Repository(database.lessonDao(),database.testDao()) }

    companion object{
        private val mutableSelectedLesson = MutableLiveData<Int>()

        val selectedLessonId: LiveData<Int> get() = mutableSelectedLesson

        fun selectLesson(id: Int) {
            mutableSelectedLesson.value = id
        }
    }
}