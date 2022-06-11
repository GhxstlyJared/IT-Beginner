package com.example.diplom2022.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import com.example.diplom2022.ApplicationConfig
import com.example.diplom2022.R
import com.example.diplom2022.database.entities.Favorite
import com.example.diplom2022.databinding.FragmentLessonBinding
import com.example.diplom2022.databinding.FragmentLessonsListBinding
import com.example.diplom2022.viewmodels.ApplicationViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_lesson.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception

class LessonFragment : Fragment() {

    private var _binding: FragmentLessonBinding? = null

    private val binding get() = _binding!!

    private val applicationViewModel: ApplicationViewModel by viewModels {
        ApplicationViewModel.DatabaseViewModelFactory((activity?.application as ApplicationConfig).repository)
    }
    private var email: String? = null
    private var bool: Boolean = false
    private val lessonId = ApplicationConfig.selectedLessonId.value

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLessonBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val currentUser = FirebaseAuth.getInstance().currentUser
        email = currentUser?.email

        email?.let {
            applicationViewModel.getFavoritesList(it).observe(viewLifecycleOwner) { favorites ->
                if (favorites.map { it.lessonId }.contains(lessonId))
                    setFavoriteBtn.setImageResource(R.drawable.ic_favorite_checked_circle)
            }
        }
        setFavoriteBtn.setOnClickListener { onFavoriteClick() }

        applicationViewModel.lessons.observe(viewLifecycleOwner) { _lessons ->
            for (el in _lessons) {
                if (el.id == lessonId) {
                    contentLessonTextView.text = el.content
                    titleLessonTextView.text = el.title
                }
            }
        }
    }


    private fun onFavoriteClick() {
        if(bool){
            setFavoriteBtn.setImageResource(R.drawable.ic_favorite_unchecked_circle)
            bool = false
        }
        else{
            setFavoriteBtn.setImageResource(R.drawable.ic_favorite_checked_circle)
            bool = true
        }
        val favorite = lessonId?.let { Favorite(0, it, "email") }
        try {
            if (favorite != null) {
                    applicationViewModel.insertFavorite(favorite)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
