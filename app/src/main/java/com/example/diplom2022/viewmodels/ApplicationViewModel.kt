package com.example.diplom2022.viewmodels

import kotlinx.coroutines.launch
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.diplom2022.database.Repository
import com.example.diplom2022.database.entities.Favorite
import com.example.diplom2022.database.entities.Lesson
import com.example.diplom2022.database.entities.Question
import com.example.diplom2022.database.entities.Test

class ApplicationViewModel(private val repository: Repository) : ViewModel() {

    val lessons: LiveData<List<Lesson>> = repository.lessons

    val tests: LiveData<List<Test>> = repository.tests

    val questions: LiveData<List<Question>> = repository.questions

    fun insertFavorite(favorite: Favorite) = viewModelScope.launch {
        repository.insertFavorite(favorite) }

    fun deleteFavorite(email : String) = viewModelScope.launch {
        repository.deleteFavorite(email)
    }

    class DatabaseViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ApplicationViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return ApplicationViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}