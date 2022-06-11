package com.example.diplom2022.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.diplom2022.ApplicationConfig
import com.example.diplom2022.R
import com.example.diplom2022.database.entities.Favorite
import com.example.diplom2022.databinding.FragmentLessonBinding
import com.example.diplom2022.viewmodels.ApplicationViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_lesson.*

class LessonFragment : Fragment() {

    private var _binding: FragmentLessonBinding? = null

    private val binding get() = _binding!!

    private val applicationViewModel: ApplicationViewModel by viewModels {
        ApplicationViewModel.DatabaseViewModelFactory((activity?.application as ApplicationConfig).repository)
    }
    private var email: String? = null
    private val lessonId = ApplicationConfig.selectedLessonId.value
    private var favorite: Favorite? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLessonBinding.inflate(inflater, container, false)

        favorite = Favorite(0)

        val currentUser = FirebaseAuth.getInstance().currentUser
        email = currentUser?.email
        favorite!!.email = currentUser?.email
        favorite!!.lessonId = lessonId

        email?.let { it ->
            applicationViewModel.getFavoritesList(it).observe(viewLifecycleOwner) { favorites ->
                if (favorites.map { favorite ->  favorite.lessonId }.contains(lessonId)) {
                    favoriteImageBtn.setImageResource(R.drawable.ic_favorite_checked_circle)
                    favoriteImageBtn.tag = true
                } else {
                    favoriteImageBtn.setImageResource(R.drawable.ic_favorite_unchecked_circle)
                    favoriteImageBtn.tag = false
                }
            }
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        favoriteImageBtn.setOnClickListener { onFavoriteClick() }

        applicationViewModel.lessons.observe(viewLifecycleOwner) { _lessons ->
            for (el in _lessons) {
                if (el.id == lessonId) {
                    contentLessonTextView.text = el.content
                    titleLessonTextView.text = el.title
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                this@LessonFragment.fragmentManager?.popBackStack()
            }
        })
    }

    private fun onFavoriteClick() {
        if (favoriteImageBtn.tag == true)
            favorite?.let { favorite -> applicationViewModel.deleteFavorite(favorite.email!!) }
        else
            favorite?.let { favorite -> applicationViewModel.insertFavorite(favorite) }
    }
}
