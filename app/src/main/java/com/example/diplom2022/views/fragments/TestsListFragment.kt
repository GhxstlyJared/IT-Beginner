package com.example.diplom2022.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.diplom2022.databinding.FragmentTestBinding
import com.example.diplom2022.viewmodels.TestsViewModel

class TestsListFragment : Fragment() {

    private var _binding: FragmentTestBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val testViewModel =
            ViewModelProvider(this)[TestsViewModel::class.java]

        _binding = FragmentTestBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textTest
        testViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}