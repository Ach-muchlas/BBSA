package com.am.bbsa.ui.customers.home.pick_up_waste

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.am.bbsa.data.response.SchedulePickUpWasteResponse
import com.am.bbsa.databinding.FragmentPickUpWasteBinding
import com.am.bbsa.service.source.Status
import com.am.bbsa.ui.auth.AuthViewModel
import com.am.bbsa.ui.customers.home.HomeViewModel
import com.am.bbsa.utils.Formatter
import com.am.bbsa.utils.UiHandler
import org.koin.android.ext.android.inject

class PickUpWasteFragment : Fragment() {
    private var _binding: FragmentPickUpWasteBinding? = null
    private val binding get() = _binding!!
    private val authViewModel: AuthViewModel by inject()
    private val viewModel: HomeViewModel by inject()

    private val token: String by lazy {
        authViewModel.getCredentialUser()?.token.toString()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPickUpWasteBinding.inflate(inflater, container, false)
        dataSetup()
        return binding.root
    }

    private fun setupView(data: SchedulePickUpWasteResponse?) {
        binding.textDate.text = Formatter.formatDate2(data?.data?.tanggal.toString())
    }

    private fun dataSetup() {
        viewModel.showSchedulePickUpWaste(token).observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                Status.LOADING -> {}
                Status.SUCCESS -> {
                    UiHandler.toastSuccessMessage(requireContext(), resource.data?.message.toString())
                    setupView(resource.data)
                }

                Status.ERROR -> {
                    UiHandler.toastErrorMessage(requireContext(), resource.message.toString())
                }
            }
        }
    }

}