package com.example.diplom2022.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.diplom2022.ApplicationConfig
import com.example.diplom2022.R
import com.example.diplom2022.databinding.FragmentTestsListBinding
import com.example.diplom2022.viewmodels.ApplicationViewModel
import com.example.diplom2022.views.adapters.TestsAdapter
import io.github.farshidroohi.extensions.onItemClickListener

class TestsListFragment : Fragment() {

    private var _binding: FragmentTestsListBinding? = null

    private val binding get() = _binding!!

    private val applicationViewModel: ApplicationViewModel by viewModels {
        ApplicationViewModel.DatabaseViewModelFactory((activity?.application as ApplicationConfig).repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentTestsListBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val testsAdapter = TestsAdapter()

        val testsList : MutableList<String> = mutableListOf()

        applicationViewModel.tests.observe(viewLifecycleOwner) { _tests ->
            for (el in _tests)
                testsList += el.title
            testsAdapter.loadedState(testsList)
        }

        val recyclerViewLocal : RecyclerView = view?.findViewById(R.id.recyclerViewTests)!!
        recyclerViewLocal.layoutManager = GridLayoutManager(context, 2)
        recyclerViewLocal.adapter = testsAdapter

        recyclerViewLocal.onItemClickListener({ position ->
            if (position == testsAdapter.itemCount - 1 && !testsAdapter.mustLoad) {
                return@onItemClickListener
            }
            ApplicationConfig.selectTest(position)
            val testFragment = TestFragment()
            this.fragmentManager?.beginTransaction()
                ?.replace(R.id.nav_host_fragment_content_menu, testFragment, "findThisFragment")
                ?.addToBackStack("testsListFragment")
                ?.commit();
        }, { position ->
            if (position == testsAdapter.itemCount - 1 && !testsAdapter.mustLoad) {
                return@onItemClickListener
            }
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}