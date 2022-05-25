package com.example.diplom2022.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.diplom2022.R
import com.example.diplom2022.databinding.FragmentInfoBinding
import com.example.diplom2022.viewmodels.InfoViewModel

class InfoFragment : Fragment() {

    private var _binding: FragmentInfoBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val infoViewModel =
            ViewModelProvider(this)[InfoViewModel::class.java]

        _binding = FragmentInfoBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val versionTextView: TextView = binding.versionTextView

        val versionName = context?.packageName?.let {
            context?.packageManager?.getPackageInfo(it, 0)?.versionName ?: "1.0.0"
        }
        infoViewModel.text.observe(viewLifecycleOwner) {
            versionTextView.append(" $versionName")
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}