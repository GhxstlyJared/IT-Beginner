package com.example.diplom2022.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.diplom2022.ApplicationConfig
import com.example.diplom2022.R
import com.example.diplom2022.databinding.FragmentLessonBinding
import com.example.diplom2022.databinding.FragmentLessonsListBinding
import com.example.diplom2022.viewmodels.*
import com.example.diplom2022.views.adapters.LessonsAdapter
import io.github.farshidroohi.extensions.onItemClickListener
import kotlinx.android.synthetic.main.fragment_lesson.*

class LessonsListFragment : Fragment() {

    private var _binding: FragmentLessonsListBinding? = null

    private val binding get() = _binding!!

    private val applicationViewModel: ApplicationViewModel by viewModels {
        ApplicationViewModel.DatabaseViewModelFactory((activity?.application as ApplicationConfig).repository)
    }

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

        val lessonsAdapter = LessonsAdapter()

        val lessons : MutableList<String> = mutableListOf()

        applicationViewModel.lessons.observe(viewLifecycleOwner) { _lessons ->
            for (el in _lessons)
                lessons += el.title
            lessonsAdapter.loadedState(lessons)
        }

        val recyclerViewLocal : RecyclerView = view?.findViewById(R.id.recyclerView)!!
        recyclerViewLocal.layoutManager = GridLayoutManager(context, 2)
        recyclerViewLocal.adapter = lessonsAdapter

        recyclerViewLocal.onItemClickListener({ position ->
            if (position == lessonsAdapter.itemCount - 1 && !lessonsAdapter.mustLoad) {
                return@onItemClickListener
            }
            Toast.makeText(context, "item click :  ${lessonsAdapter.getItem(position)}$position", Toast.LENGTH_SHORT).show()
            ApplicationConfig.selectLesson(position)
            val lessonFragment = LessonFragment()
            this.fragmentManager?.beginTransaction()
                ?.replace(R.id.nav_host_fragment_content_menu, lessonFragment, "findThisFragment")
                ?.addToBackStack("LessonsListFragment")
                ?.commit();
        }, { position ->
            if (position == lessonsAdapter.itemCount - 1 && !lessonsAdapter.mustLoad) {
                return@onItemClickListener
            }
            Toast.makeText(context, "item long click :  ${lessonsAdapter.getItem(position)}$position", Toast.LENGTH_SHORT).show()
        })

    }
}