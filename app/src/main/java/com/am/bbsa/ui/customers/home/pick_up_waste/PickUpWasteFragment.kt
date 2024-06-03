package com.am.bbsa.ui.customers.home.pick_up_waste

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.am.bbsa.R
import com.am.bbsa.adapter.home.HistoryRegistrantPickupWasteAdapter
import com.am.bbsa.data.response.HistoryRegistrantPickupWasteResponse
import com.am.bbsa.data.response.SchedulePickUpWasteResponse
import com.am.bbsa.databinding.FragmentPickUpWasteBinding
import com.am.bbsa.service.source.Status
import com.am.bbsa.ui.auth.AuthViewModel
import com.am.bbsa.ui.customers.home.HomeViewModel
import com.am.bbsa.utils.Destination
import com.am.bbsa.utils.Formatter
import com.am.bbsa.utils.Navigation
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
        initialize()
        setupGetDataFromAPi()
        setupNavigation()
        return binding.root
    }

    private fun initialize() {
        UiHandler.setupVisibilityBottomNavigationNasabah(activity, true)
        binding.ViewBar.textTitleAppBar.text = getString(R.string.pick_up_waste)
    }

    private fun setupNavigation() {
        binding.ViewBar.buttonBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setupView(data: SchedulePickUpWasteResponse?) {
        val date = data?.data?.tanggal.toString()
        val textDate = date.ifEmpty { "" }
        if (data?.data?.tanggal == null) {
            binding.textRegister.text = getString(R.string.empty_schedule_pick_up)
            binding.buttonRegister.visibility = View.INVISIBLE
        } else {
            binding.textRegister.text = getString(R.string.register_pick_up_waste)
            binding.buttonRegister.visibility = View.VISIBLE
            binding.buttonRegister.setOnClickListener {
                val bundle = Bundle().apply { putInt(BUNDLE_id, data.data.id ?: 0) }
                Navigation.navigationFragment(
                    Destination.PICK_UP_WASTE_TO_REGISTER_PICK_UP_WASTE,
                    findNavController(),
                    bundle
                )
            }
        }
        binding.textDate.text = Formatter.formatDate2(textDate)
    }

    private fun setupGetDataFromAPi() {
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

        viewModel.showHistoryRegistrantPickupWaste(token).observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                Status.LOADING -> {}
                Status.SUCCESS -> {
                    setupAdapter(resource.data)
                }

                Status.ERROR -> {
                    UiHandler.toastErrorMessage(requireContext(), resource.message.toString())
                }
            }
        }
    }

    private fun setupAdapter(data: HistoryRegistrantPickupWasteResponse?) {
        val adapter = HistoryRegistrantPickupWasteAdapter().apply {
            submitList(data?.data)
        }

        binding.cardHistory.recyclerView.let {
            it.adapter = adapter
            it.layoutManager = LinearLayoutManager(requireContext())
        }
    }


    companion object {
        const val BUNDLE_id = "id_pick_up_waste"
    }

    override fun onDestroy() {
        super.onDestroy()
        UiHandler.setupVisibilityBottomNavigationNasabah(activity, false)
    }
}