package com.example.diplom2022.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.diplom2022.ApplicationConfig
import com.example.diplom2022.R
import com.example.diplom2022.viewmodels.ApplicationViewModel
import kotlinx.android.synthetic.main.fragment_lesson.*


class LessonFragment : Fragment() {

    private val applicationViewModel: ApplicationViewModel by viewModels {
        ApplicationViewModel.DatabaseViewModelFactory((activity?.application as ApplicationConfig).repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val lessonId = ApplicationConfig.selectedLessonId.value

        applicationViewModel.lessons.observe(viewLifecycleOwner) { _lessons ->
            for (el in _lessons) {
                println("test ${el.id} $lessonId")
                if (el.id == lessonId) {
                    println("TEST ${el.content}")
                    contentLessontextView.text = el.content
                }
            }
        }
        return inflater.inflate(R.layout.fragment_lesson, container, false)
    }
}