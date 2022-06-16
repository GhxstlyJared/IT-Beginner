package com.example.diplom2022.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.diplom2022.ApplicationConfig
import com.example.diplom2022.R
import com.example.diplom2022.database.entities.Lesson
import com.example.diplom2022.databinding.FragmentLessonsListBinding
import com.example.diplom2022.models.SharedPref
import com.example.diplom2022.viewmodels.*
import com.example.diplom2022.views.adapters.LessonsAdapter
import com.google.firebase.auth.FirebaseAuth
import io.github.farshidroohi.extensions.onItemClickListener
import kotlinx.android.synthetic.main.fragment_lessons_list.*
import kotlinx.android.synthetic.main.fragment_settings.*
import java.util.*
import kotlin.collections.HashMap

class LessonsListFragment : Fragment(), SearchView.OnQueryTextListener {

    private var _binding: FragmentLessonsListBinding? = null

    private val binding get() = _binding!!

    private val applicationViewModel: ApplicationViewModel by viewModels {
        ApplicationViewModel.DatabaseViewModelFactory((activity?.application as ApplicationConfig).repository)
    }

    private lateinit var lessonsAdapter: LessonsAdapter
    private var titlesLessonList = ArrayList<String>(20)
    private var titlesLessonListBuff = ArrayList<String>()
    private var titlesLessonListFavorites = hashMapOf<String,Boolean>()

    private lateinit var sharedPreferences: SharedPref

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLessonsListBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val email = FirebaseAuth.getInstance().currentUser?.email

        sharedPreferences = context?.let { SharedPref(it) }!!

        lessonsAdapter = LessonsAdapter(applicationViewModel, viewLifecycleOwner, email)
        applicationViewModel.lessons.observe(viewLifecycleOwner) { _lessons ->
            for (el in _lessons) {
                titlesLessonList += el.title
            }

            if (sharedPreferences.loadFavoriteState() == true) {
                titlesLessonList.clear()
                email?.let {
                    applicationViewModel.getFavoritesList(it).observe(viewLifecycleOwner){ favoritesList ->
                        for(el in _lessons){
                            titlesLessonListFavorites[el.title] = favoritesList.map { it.lessonId }.contains(el.id)
                        }
                        titlesLessonList.addAll(titlesLessonListFavorites.map{ it.key to it.value }.sortedBy { !it.second }.toMap().map { it.key })
                        titlesLessonListBuff = titlesLessonList
                        lessonsAdapter.loadedState(titlesLessonList)
                    }
                }
            }
            else{
                titlesLessonListBuff = titlesLessonList
                lessonsAdapter.loadedState(titlesLessonList)
            }
        }

        val recyclerViewLocal: RecyclerView = view?.findViewById(R.id.recyclerViewLessons)!!
        recyclerViewLocal.layoutManager = GridLayoutManager(context, 2)
        recyclerViewLocal.adapter = lessonsAdapter

        recyclerViewLocal.onItemClickListener({ position ->
            if (position == lessonsAdapter.itemCount - 1 && !lessonsAdapter.mustLoad) {
                return@onItemClickListener
            }
            ApplicationConfig.selectLesson(position)
            val lessonFragment = LessonFragment()
            this.fragmentManager?.beginTransaction()
                ?.replace(R.id.nav_host_fragment_content_menu, lessonFragment, "findThisFragment")
                ?.addToBackStack("lessonsListFragment")
                ?.commit();
        }, { position ->
            if (position == lessonsAdapter.itemCount - 1 && !lessonsAdapter.mustLoad) {
                return@onItemClickListener
            }
        })

        searchView.setOnQueryTextListener(this)
    }

    fun filter(charText: String) {
        var charText = charText
        charText = charText.toLowerCase(Locale.getDefault())
        lessonsAdapter.items.clear()
        if (charText.isEmpty()) {
            titlesLessonListBuff.let { lessonsAdapter.items.addAll(it) }
        } else {
            for (wp in titlesLessonListBuff) {
                if (wp.toLowerCase(Locale.getDefault()).contains(charText)) {
                    lessonsAdapter.items.add(wp)
                }
            }
        }
        lessonsAdapter.notifyDataSetChanged()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String): Boolean {
        filter(newText)
        return false
    }

}