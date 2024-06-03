package com.am.bbsa.ui.admin.menu.update_price

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.am.bbsa.R
import com.am.bbsa.adapter.type_waste.UpdatePriceWasteAdapter
import com.am.bbsa.data.response.SampahResponse
import com.am.bbsa.databinding.FragmentUpdatePriceWasteBinding
import com.am.bbsa.service.source.Status
import com.am.bbsa.ui.admin.menu.MenuViewModel
import com.am.bbsa.ui.auth.AuthViewModel
import com.am.bbsa.utils.UiHandler
import org.koin.android.ext.android.inject

class UpdatePriceWasteFragment : Fragment() {

    private var _binding: FragmentUpdatePriceWasteBinding? = null
    private val binding get() = _binding!!
    private val authViewModel: AuthViewModel by inject()
    private val viewModel: MenuViewModel by inject()
    private val token: String by lazy {
        authViewModel.getCredentialUser()?.token.toString()
    }
    private val adapter = UpdatePriceWasteAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpdatePriceWasteBinding.inflate(inflater, container, false)
        setupView()
        setupGetDataTypeWasteFromApi()
        setupNavigation()

        return binding.root
    }

    private fun setupView() {
        UiHandler.setupVisibilityBottomNavigationAdmin(activity, true)
        binding.viewAppBar.textTitleAppBar.text = getString(R.string.update_price_waste)
    }

    private fun setupNavigation() {
        binding.viewAppBar.buttonBack.setOnClickListener { findNavController().popBackStack() }
        binding.buttonSave.setOnClickListener {
            setupUpdatePrice()
        }
    }

    private fun setupAdapter(data: SampahResponse?) {
        adapter.submitList(data?.data)
        binding.recyclerView.let {
            it.adapter = adapter
            it.layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun setupGetDataTypeWasteFromApi() {
        viewModel.showAllInformationWaste(token).observe(viewLifecycleOwner) { resource ->
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


    private fun setupUpdatePrice() {
        val data = adapter.currentList.map { listOf(it.id, it.price!!) }
        viewModel.updatePriceWaste(token, data).observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                Status.LOADING -> {
                    UiHandler.toastSuccessMessage(requireContext(), "Mohon tunggu sebentar...")
                }

                Status.SUCCESS -> {
                    setupGetDataTypeWasteFromApi()
                    UiHandler.toastSuccessMessage(
                        requireContext(),
                        resource.data?.message.toString()
                    )
                }

                Status.ERROR -> {
                    UiHandler.toastErrorMessage(requireContext(), resource.message.toString())
                }
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        UiHandler.setupVisibilityBottomNavigationAdmin(activity, false)
    }
}