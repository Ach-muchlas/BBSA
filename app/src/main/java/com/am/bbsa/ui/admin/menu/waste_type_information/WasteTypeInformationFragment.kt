package com.am.bbsa.ui.admin.menu.waste_type_information

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.am.bbsa.adapter.type_waste.TypeWasteAdapter
import com.am.bbsa.data.response.SampahResponse
import com.am.bbsa.databinding.FragmentWasteTypeInformationBinding
import com.am.bbsa.service.source.Status
import com.am.bbsa.ui.admin.menu.MenuViewModel
import com.am.bbsa.ui.auth.AuthViewModel
import com.am.bbsa.utils.Destination
import com.am.bbsa.utils.Navigation
import com.am.bbsa.utils.UiHandler
import org.koin.android.ext.android.inject

class WasteTypeInformationFragment : Fragment() {
    private var _binding: FragmentWasteTypeInformationBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MenuViewModel by inject()
    private val authViewModel: AuthViewModel by inject()
    private val token by lazy {
        authViewModel.getCredentialUser()?.token.toString()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWasteTypeInformationBinding.inflate(inflater, container, false)
        UiHandler.setupVisibilityBottomNavigationAdmin(activity, true)
        setupNavigation()
        displayWasteInformation()
        return binding.root
    }

    private fun setupNavigation() {
        binding.buttonBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.buttonAdd.setOnClickListener {
            Navigation.navigationFragment(
                Destination.WASTE_TYPE_INFORMATION_TO_ADD_WASTE_TYPE,
                findNavController()
            )
        }
    }

    private fun displayWasteInformation() {
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

    private fun setupDeleteWaste(id: Int) {
        viewModel.deleteInformationWaste(id, token)
            .observe(viewLifecycleOwner) { resource ->
                when (resource.status) {
                    Status.LOADING -> {}
                    Status.SUCCESS -> {
                        UiHandler.toastSuccessMessage(
                            requireContext(),
                            resource.data?.message.toString()
                        )
                        viewModel.showAllInformationWaste(token)
                            .observe(viewLifecycleOwner) { resources ->
                                when (resources.status) {
                                    Status.LOADING -> {}
                                    Status.SUCCESS -> {
                                        setupAdapter(resources.data)
                                    }

                                    Status.ERROR -> {
                                        UiHandler.toastErrorMessage(
                                            requireContext(),
                                            resource.message.toString()
                                        )
                                    }
                                }
                            }
                    }

                    Status.ERROR -> {
                        UiHandler.toastErrorMessage(
                            requireContext(),
                            resource.data?.message.toString()
                        )
                    }
                }
            }
    }

    private fun setupAdapter(data: SampahResponse?) {
        val adapter = TypeWasteAdapter(false).apply {
            submitList(data?.data)
            callbackOnclickEdit = {
                val bundle = Bundle().apply {
                    putParcelable(KEY_DATA_SAMPAH, it)
                }
                Navigation.navigationFragment(
                    Destination.WASTE_TYPE_INFORMATION_TO_UPDATE_WASTE_TYPE,
                    findNavController(),
                    bundle
                )
            }
            callbackOnclickDelete = { id ->
                setupDeleteWaste(id)
            }
        }

        binding.recyclerViewTypeWaste.let {
            it.adapter = adapter
            it.layoutManager = LinearLayoutManager(requireContext())
        }
    }

    companion object {
        const val KEY_DATA_SAMPAH = "data_sampah"
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        UiHandler.setupVisibilityBottomNavigationAdmin(activity, false)
    }
}