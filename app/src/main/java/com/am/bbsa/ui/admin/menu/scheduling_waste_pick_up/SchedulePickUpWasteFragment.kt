package com.am.bbsa.ui.admin.menu.scheduling_waste_pick_up

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.am.bbsa.R
import com.am.bbsa.adapter.schedule_pick_up.NasabahRegistrantPickupWasteAdapter
import com.am.bbsa.data.response.DataItemNasabahRegistrantPickupWaste
import com.am.bbsa.data.response.NasabahRegistrantPickupWasteResponse
import com.am.bbsa.data.response.SchedulePickUpWasteResponse
import com.am.bbsa.databinding.FragmentSchedulePickUpWasteBinding
import com.am.bbsa.service.source.Status
import com.am.bbsa.ui.admin.menu.MenuViewModel
import com.am.bbsa.ui.auth.AuthViewModel
import com.am.bbsa.utils.Destination
import com.am.bbsa.utils.Formatter
import com.am.bbsa.utils.Navigation
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
        initialize()
        setupGetDataSchedulePickupFromApi()
        setupGetDataNasabahRegistrantPickupWasteFromApi()
        setupGetDataApproveNasabahPickupWasteFromApi()
        setupNavigation()
        return binding.root
    }

    private fun initialize() {
        UiHandler.setupVisibilityBottomNavigationAdmin(activity, true)
        binding.viewAppBar.textTitleAppBar.text = getString(R.string.pick_up_waste)
        binding.cardListRegisNasabah.textListNasabah.text =
            getString(R.string.list_nasabah_scheduling)
        binding.cardApproveRegis.textListNasabah.text = getString(R.string.list_nasabah_approved)
    }

    private fun setupNavigation() {
        binding.buttonAdd.setOnClickListener {
            Navigation.navigationFragment(
                Destination.SCHEDULING_PICK_UP_TO_ADD_SCHEDULE,
                findNavController()
            )
        }
        binding.viewAppBar.buttonBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }


    private fun setupView(data: SchedulePickUpWasteResponse?) {
        if (data?.data?.tanggal == null) {
            binding.textValueScheduling.text = ""
        } else {
            binding.textValueScheduling.text = Formatter.formatDate2(data.data.tanggal.toString())
        }
    }

    private fun setupGetDataSchedulePickupFromApi() {
        viewModel.showSchedulePickupWaste(token).observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                Status.LOADING -> {
                    UiHandler.manageShimmer(binding.shimmerContainerTextDate, true)
                }

                Status.SUCCESS -> {
                    UiHandler.manageShimmer(binding.shimmerContainerTextDate, false)
                    binding.textValueScheduling.visibility = View.VISIBLE
                    setupView(resource.data)
                }

                Status.ERROR -> {
                    binding.textValueScheduling.visibility = View.VISIBLE
                    UiHandler.manageShimmer(binding.shimmerContainerTextDate, false)
                    UiHandler.toastErrorMessage(requireContext(), resource.message.toString())
                }
            }
        }
    }

    private fun setupGetDataNasabahRegistrantPickupWasteFromApi() {
        viewModel.showNasabahRegistrantPickupWaste(token).observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                Status.LOADING -> {}
                Status.SUCCESS -> {
                    setupAdapterNasabahRegistrantPickup(resource.data as List<DataItemNasabahRegistrantPickupWaste>?)
                }

                Status.ERROR -> {
                    UiHandler.toastErrorMessage(requireContext(), resource.message.toString())
                }
            }
        }
    }

    private fun setupGetDataApproveNasabahPickupWasteFromApi() {
        viewModel.showApprovedNasabahRegistrantPickupWaste(token)
            .observe(viewLifecycleOwner) { resource ->
                when (resource.status) {
                    Status.LOADING -> {}
                    Status.SUCCESS -> {
                        setupAdapterApproveNasabahPickup(resource.data)
                    }

                    Status.ERROR -> {
                        UiHandler.toastErrorMessage(requireContext(), resource.message.toString())
                    }
                }
            }
    }

    private fun setupAdapterNasabahRegistrantPickup(data: List<DataItemNasabahRegistrantPickupWaste>?) {
        val adapter = NasabahRegistrantPickupWasteAdapter(false).apply {
            submitList(data)
            setOnApprovedListener { id ->
                changeStatusRegistrantPickupWaste(id, "Diterima")
            }
            setOnRejectListener { id ->
                changeStatusRegistrantPickupWaste(id, "Ditolak")
            }
            setOnclickListener { id ->
                val bundle = Bundle().apply {
                    putInt(BUNDLE_ID, id)
                }
                Navigation.navigationFragment(
                    Destination.SCHEDULE_PICK_UP_WASTE_TO_DETAIL_SCHEDULE_PICK_UP_WASTE,
                    findNavController(),
                    bundle
                )
            }
        }
        binding.cardListRegisNasabah.recyclerViewNasabahRegistrant.let {
            it.adapter = adapter
            it.layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun setupAdapterApproveNasabahPickup(data: NasabahRegistrantPickupWasteResponse?) {
        val adapter = NasabahRegistrantPickupWasteAdapter(true).apply {
            submitList(data?.data)
            setOnclickListener { id ->
                val bundle = Bundle().apply {
                    putInt(BUNDLE_ID, id)
                }
                Navigation.navigationFragment(
                    Destination.SCHEDULE_PICK_UP_WASTE_TO_DETAIL_SCHEDULE_PICK_UP_WASTE,
                    findNavController(),
                    bundle
                )
            }
        }
        binding.cardApproveRegis.recyclerViewNasabahRegistrant.let {
            it.adapter = adapter
            it.layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun changeStatusRegistrantPickupWaste(id: Int, status: String) {
        viewModel.changeStatusRegistrantPickupWaste(token, id, status)
            .observe(viewLifecycleOwner) { resource ->
                when (resource.status) {
                    Status.LOADING -> {}
                    Status.SUCCESS -> {
                        UiHandler.toastSuccessMessage(
                            requireContext(),
                            resource.data?.message.toString()
                        )
                        setupGetDataNasabahRegistrantPickupWasteFromApi()
                        setupGetDataApproveNasabahPickupWasteFromApi()
                    }

                    Status.ERROR -> {
                        UiHandler.toastErrorMessage(requireContext(), resource.message.toString())
                    }
                }
            }
    }


    companion object {
        const val BUNDLE_ID = "id"
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        UiHandler.setupVisibilityBottomNavigationAdmin(activity, false)
    }
}