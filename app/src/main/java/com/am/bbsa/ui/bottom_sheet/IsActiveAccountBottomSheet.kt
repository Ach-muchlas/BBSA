package com.am.bbsa.ui.bottom_sheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.am.bbsa.databinding.FragmentIsActiveAccountBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class IsActiveAccountBottomSheet : BottomSheetDialogFragment() {
    private var _binding: FragmentIsActiveAccountBottomSheetBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentIsActiveAccountBottomSheetBinding.inflate(inflater, container, false)
        binding.buttonClose.setOnClickListener { dismiss() }
        return binding.root
    }


}