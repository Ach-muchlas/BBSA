package com.am.bbsa.ui.customers.home.history_deposit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.am.bbsa.databinding.FragmentHistoryDepositWasteBinding

class HistoryDepositWasteFragment : Fragment() {
    private var _binding: FragmentHistoryDepositWasteBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryDepositWasteBinding.inflate(inflater, container, false)

        return binding.root
    }

}