package com.am.bbsa.ui.customers.account.change_language

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.am.bbsa.R
import com.am.bbsa.databinding.FragmentChangeLanguageBinding
import com.am.bbsa.utils.UiHandler

class ChangeLanguageFragment : Fragment() {

    private var _binding : FragmentChangeLanguageBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChangeLanguageBinding.inflate(inflater, container, false)
        UiHandler.setupVisibilityBottomNavigationNasabah(activity, true)
        return binding.root
    }

    private fun setupNavigation(){
        binding.viewAppBar.buttonBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroy() {
        UiHandler.setupVisibilityBottomNavigationNasabah(activity, false)
        super.onDestroy()
    }
}