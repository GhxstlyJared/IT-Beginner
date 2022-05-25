package com.example.diplom2022

import android.app.Application
import com.example.diplom2022.database.AppRoomDatabase
import com.example.diplom2022.database.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class ApplicationConfig : Application() {
    val database by lazy { AppRoomDatabase.getInstance(this) }
    val repository by lazy { Repository(database.lessonDao(),database.testDao()) }
}