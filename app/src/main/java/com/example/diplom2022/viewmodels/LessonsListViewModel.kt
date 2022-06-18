package com.example.diplom2022.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.diplom2022.database.entities.Lesson

class LessonsListViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Здесь будут уроки"
    }
    val text: LiveData<String> = _text
}