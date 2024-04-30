package com.am.bbsa.ui.admin.menu.scheduling_waste_pick_up

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.am.bbsa.R
import com.am.bbsa.data.response.SchedulePickUpWasteResponse
import com.am.bbsa.databinding.FragmentSchedulePickUpWasteBinding
import com.am.bbsa.service.source.Status
import com.am.bbsa.ui.admin.menu.MenuViewModel
import com.am.bbsa.ui.auth.AuthViewModel
import com.am.bbsa.utils.Formatter
import com.am.bbsa.utils.UiHandler
import org.koin.android.ext.android.inject

class SchedulePickUpWasteFragment : Fragment() {
    private var _binding: FragmentSchedulePickUpWasteBinding? = null
    private val binding get() = _binding!!

    private val authViewModel: AuthViewModel by inject()
    private val viewModel: MenuViewModel by inject()

    private val token: String by lazy {
        authViewModel.getCredentialUser()?.token.toString()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSchedulePickUpWasteBinding.inflate(inflater, container, false)
        setupDataForApi()
        UiHandler.setupVisibilityBottomNavigationAdmin(activity, true)
        binding.viewAppBar.textTitleAppBar.text = getString(R.string.pick_up_waste)
        return binding.root
    }

    private fun setupView(data: SchedulePickUpWasteResponse?) {
        binding.textValueScheduling.text = Formatter.formatDate2(data?.data?.tanggal.toString())
    }

    private fun setupDataForApi() {
        viewModel.showSchedulePickupWaste(token).observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                Status.LOADING -> {}
                Status.SUCCESS -> {
                    setupView(resource.data)
                }

                Status.ERROR -> {
                    UiHandler.toastErrorMessage(requireContext(), resource.message.toString())
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        UiHandler.setupVisibilityBottomNavigationAdmin(activity, false)
    }
}