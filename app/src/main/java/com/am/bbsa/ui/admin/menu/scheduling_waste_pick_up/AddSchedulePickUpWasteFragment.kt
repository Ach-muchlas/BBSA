package com.am.bbsa.ui.admin.menu.scheduling_waste_pick_up

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.am.bbsa.databinding.FragmentAddSchedulePickUpWasteBinding

class AddSchedulePickUpWasteFragment : Fragment() {

    private var _binding: FragmentAddSchedulePickUpWasteBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddSchedulePickUpWasteBinding.inflate(inflater, container, false)

        return binding.root
    }
}