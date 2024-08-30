package com.am.bbsa.ui.customers.home.type_waste

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.am.bbsa.adapter.type_waste.TypeWasteAdapter
import com.am.bbsa.data.response.SampahResponse
import com.am.bbsa.databinding.FragmentTypeWasteBinding
import com.am.bbsa.service.source.Status
import com.am.bbsa.ui.auth.AuthViewModel
import com.am.bbsa.ui.customers.home.HomeViewModel
import com.am.bbsa.utils.UiHandler
import org.koin.android.ext.android.inject

class TypeWasteFragment : Fragment() {
    private var _binding: FragmentTypeWasteBinding? = null
    private val binding get() = _binding!!
    private val authViewModel: AuthViewModel by inject()
    private val viewModel: HomeViewModel by inject()
    private val token by lazy {
        authViewModel.getCredentialUser()?.token.toString()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTypeWasteBinding.inflate(inflater, container, false)
        UiHandler.setupVisibilityBottomNavigationNasabah(activity, true)
        binding.viewAppBar.textTitleAppBar.text = "Jenis Sampah"
        setupNavigation()
        displayTypeWaste()
        return binding.root
    }

    private fun setupNavigation() {
        binding.viewAppBar.buttonBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun displayTypeWaste() {
        viewModel.showAllTypeWaste(token).observe(viewLifecycleOwner) { resource ->
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

    private fun setupAdapter(data: SampahResponse?) {
        val adapter = TypeWasteAdapter(true).apply {
            submitList(data?.data)
        }

        binding.recyclerViewTypeWaste.let {
            it.adapter = adapter
            it.layoutManager = LinearLayoutManager(requireContext())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        UiHandler.setupVisibilityBottomNavigationNasabah(activity, false)
    }
}