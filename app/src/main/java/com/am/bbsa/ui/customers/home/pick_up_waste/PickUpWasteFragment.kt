package com.am.bbsa.ui.customers.home.pick_up_waste

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.am.bbsa.R
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
        UiHandler.setupVisibilityBottomNavigationNasabah(activity, true)
        setupGetDataSchedulePickupFromApi()
        setupGetDataHistoryPickUpFromApi()
        setupNavigation()
        return binding.root
    }

    private fun setupGetDataHistoryPickUpFromApi() {

    }

    private fun setupNavigation() {
        binding.ViewBar.buttonBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setupView(data: SchedulePickUpWasteResponse?) {
        val date = data?.data?.tanggal.toString()
        val textDate = date.ifEmpty { "" }
        val registerText =
            if (date.isNotEmpty()) getString(R.string.register_pick_up_waste) else getString(R.string.empty_schedule_pick_up)
        binding.textRegister.text = registerText
        binding.textDate.text = Formatter.formatDate2(textDate)
        binding.buttonRegister.isEnabled = date.isNotEmpty()
    }

    private fun setupGetDataSchedulePickupFromApi() {
        viewModel.showSchedulePickUpWaste(token).observe(viewLifecycleOwner) { resource ->
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
        UiHandler.setupVisibilityBottomNavigationNasabah(activity, false)
    }
}