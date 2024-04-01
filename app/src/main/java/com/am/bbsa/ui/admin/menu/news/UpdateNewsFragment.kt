package com.am.bbsa.ui.admin.menu.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.am.bbsa.databinding.FragmentAddOrUpdateNewsBinding

class UpdateNewsFragment : Fragment() {
    private var _binding: FragmentAddOrUpdateNewsBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddOrUpdateNewsBinding.inflate(inflater, container, false)

        return binding.root
    }
}