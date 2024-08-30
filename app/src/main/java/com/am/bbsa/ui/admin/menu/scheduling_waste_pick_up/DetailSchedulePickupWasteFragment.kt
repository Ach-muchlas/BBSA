package com.am.bbsa.ui.admin.menu.scheduling_waste_pick_up

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.am.bbsa.R
import com.am.bbsa.data.response.DataDetailNasabahRegistrantPickup
import com.am.bbsa.data.response.DetailNasabahRegistrantPickupWasteResponse
import com.am.bbsa.databinding.FragmentDetailSchedulePickupWasteBinding
import com.am.bbsa.service.source.Status
import com.am.bbsa.ui.admin.menu.MenuViewModel
import com.am.bbsa.ui.auth.AuthViewModel
import com.am.bbsa.utils.UiHandler
import com.bumptech.glide.Glide
import org.koin.android.ext.android.inject

class DetailSchedulePickupWasteFragment : Fragment() {
    private var _binding: FragmentDetailSchedulePickupWasteBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MenuViewModel by inject()
    private val authViewModel: AuthViewModel by inject()
    private val token: String by lazy { authViewModel.getCredentialUser()?.token.toString() }
    private val receiveId: Int by lazy {
        arguments?.getInt(SchedulePickUpWasteFragment.BUNDLE_ID) ?: 0
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailSchedulePickupWasteBinding.inflate(inflater, container, false)
        initialize()
        setupNavigation()
        setupGetDataFromApi()
        return binding.root
    }

    private fun initialize() {
        UiHandler.setupVisibilityBottomNavigationAdmin(activity, true)
        binding.viewAppBar.textTitleAppBar.text =
            getString(R.string.detail_registrant_pick_up_waste)
    }

    private fun setupNavigation() {
        binding.viewAppBar.buttonBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setupIsVisibilityView() {
        binding.textValueName.visibility = View.VISIBLE
        binding.textValueDesc.visibility = View.VISIBLE
        binding.imageWaste.visibility = View.VISIBLE
    }

    private fun showShimmer(isVisible: Boolean) {
        UiHandler.manageShimmer(binding.shimmerContainerName, isVisible)
        UiHandler.manageShimmer(binding.shimmerContainerDescription, isVisible)
        UiHandler.manageShimmer(binding.shimmerContainerImage, isVisible)
    }


    private fun setupView(data: DataDetailNasabahRegistrantPickup?) {
        binding.textValueName.text = data?.userName.toString()
        binding.textValueDesc.text = data?.deskripsi.toString()
        Glide.with(requireContext()).load(data?.foto).into(binding.imageWaste)
    }

    private fun setupGetDataFromApi() {
        viewModel.showDetailNasabahPickupWaste(token, receiveId)
            .observe(viewLifecycleOwner) { resource ->
                when (resource.status) {
                    Status.LOADING -> {
                        showShimmer(true)
                    }

                    Status.SUCCESS -> {
                        val data = resource.data?.data
                        showShimmer(false)
                        setupIsVisibilityView()
                        setupView(data)
                    }

                    Status.ERROR -> {
                        showShimmer(false)
                        setupIsVisibilityView()
                        UiHandler.toastErrorMessage(requireContext(), resource.message.toString())
                    }
                }
            }
    }
}