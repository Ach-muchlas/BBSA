package com.am.bbsa.ui.customers.account.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.am.bbsa.databinding.FragmentUpdateProfileBinding
import com.am.bbsa.service.source.Status
import com.am.bbsa.ui.auth.AuthViewModel
import com.am.bbsa.ui.customers.account.AccountViewModel
import com.am.bbsa.utils.UiHandler
import org.koin.android.ext.android.inject

class UpdateProfileFragment : Fragment() {
    private var _binding: FragmentUpdateProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AccountViewModel by inject()
    private val authViewModel: AuthViewModel by inject()

    private val token: String by lazy { authViewModel.getCredentialUser()?.token.toString() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpdateProfileBinding.inflate(inflater, container, false)
        UiHandler.setupVisibilityBottomNavigationNasabah(activity, true)
        setupNavigation()
        setupDisplay()
        return binding.root
    }

    private fun setupDisplay() {
        val receive = arguments
        val bundleData = receive?.getString(ProfileFragment.KEY_TEXT_VIEW_BAR_UPDATE_PROFILE)
        val textValue = binding.edtText.text
        binding.viewAppbar.textTitleAppBar.text = "Ubah $bundleData"
        binding.text.text = bundleData

        binding.buttonSave.setOnClickListener {
            if (bundleData.toString() == "Nama") {
                setupUpdateNameUser(textValue.toString())
            } else if (bundleData.toString() == "NIK") {
                setupUpdateNIKUser(textValue.toString())
            } else if (bundleData.toString() == "Nomor Telephone") {
                setupUpdatePhoneNumberUser(textValue.toString())
            }
        }
    }

    private fun setupNavigation() {
        binding.viewAppbar.buttonBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setupUpdateNameUser(name: String) {
        viewModel.updateNameUser(token, name).observe(viewLifecycleOwner) { resources ->
            when (resources.status) {
                Status.LOADING -> {}
                Status.SUCCESS -> {
                    UiHandler.toastSuccessMessage(
                        requireContext(),
                        resources.data?.message.toString()
                    )
                    findNavController().popBackStack()
                }

                Status.ERROR -> {
                    UiHandler.toastErrorMessage(requireContext(), resources.message.toString())
                }
            }
        }
    }

    private fun setupUpdateNIKUser(NIK: String) {
        viewModel.updateNIKUser(token, NIK).observe(viewLifecycleOwner) { resources ->
            when (resources.status) {
                Status.LOADING -> {}
                Status.SUCCESS -> {
                    UiHandler.toastSuccessMessage(
                        requireContext(),
                        resources.data?.message.toString()
                    )
                    findNavController().popBackStack()
                }

                Status.ERROR -> {
                    UiHandler.toastErrorMessage(requireContext(), resources.message.toString())
                }
            }
        }
    }

    private fun setupUpdatePhoneNumberUser(phoneNumber: String) {
        viewModel.updatePhoneNumberUser(token, phoneNumber)
            .observe(viewLifecycleOwner) { resources ->
                when (resources.status) {
                    Status.LOADING -> {}
                    Status.SUCCESS -> {
                        UiHandler.toastSuccessMessage(
                            requireContext(),
                            resources.data?.message.toString()
                        )
                        findNavController().popBackStack()
                    }

                    Status.ERROR -> {
                        UiHandler.toastErrorMessage(requireContext(), resources.message.toString())
                    }
                }
            }
    }
}