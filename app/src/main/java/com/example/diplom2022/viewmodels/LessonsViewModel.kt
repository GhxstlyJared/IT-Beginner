package com.example.diplom2022.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LessonsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Здесь будут уроки"
    }
    val text: LiveData<String> = _text
}