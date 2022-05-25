package com.example.diplom2022.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GuideViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Руководство пользователя в разработке"
    }
    val text: LiveData<String> = _text
}