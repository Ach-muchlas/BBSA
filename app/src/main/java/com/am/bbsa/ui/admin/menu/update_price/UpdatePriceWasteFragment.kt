package com.am.bbsa.ui.admin.menu.update_price

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.am.bbsa.R
import com.am.bbsa.databinding.FragmentUpdatePriceWasteBinding
import com.am.bbsa.utils.UiHandler

class UpdatePriceWasteFragment : Fragment() {

    private var _binding : FragmentUpdatePriceWasteBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpdatePriceWasteBinding.inflate(inflater, container, false)
        UiHandler.setupVisibilityBottomNavigationAdmin(activity, true)
        binding.viewAppBar.textTitleAppBar.text = "Update Harga Sampah"
        binding.viewAppBar.buttonBack.setOnClickListener { findNavController().popBackStack() }
        return binding.root
    }
}