package com.am.bbsa.ui.customers.account.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.am.bbsa.R
import com.am.bbsa.databinding.FragmentUpdateProfileBinding
import com.am.bbsa.service.source.Status
import com.am.bbsa.ui.auth.AuthViewModel
import com.am.bbsa.ui.customers.account.AccountViewModel
import com.am.bbsa.utils.KEY
import com.am.bbsa.utils.UiHandler
import org.koin.android.ext.android.inject

class UpdateProfileFragment : Fragment() {
    private var _binding: FragmentUpdateProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AccountViewModel by inject()
    private val authViewModel: AuthViewModel by inject()
    private val token: String by lazy { authViewModel.getCredentialUser()?.token.toString() }
    private val receiveTitleContent: String by lazy {
        arguments?.getString(KEY.KEY_TEXT_VIEW_BAR_UPDATE_PROFILE).toString()
    }
    private val receiveValueContent: String by lazy {
        arguments?.getString(KEY.BUNDLE_KEY_VALUE).toString()
    }

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
        val textValue = binding.edtText.text
        binding.viewAppbar.textTitleAppBar.text = buildString {
            append("Ubah ")
            append(receiveTitleContent)
        }
        if (receiveValueContent == "null") {
            binding.edtText.setText("")
            binding.edtText.hint = getString(R.string.input_your_data)
        } else {
            binding.edtText.setText(receiveValueContent)
        }
        binding.text.text = receiveTitleContent

        binding.buttonSave.setOnClickListener {
            when (receiveTitleContent) {
                KEY.KEY_TITLE_NAME_NAVIGATION_PROFILE_TO_UPDATE_PROFILE -> {
                    setupUpdateNameUser(textValue.toString())
                }

                KEY.KEY_TITLE_NIK_NAVIGATION_PROFILE_TO_UPDATE_PROFILE -> {
                    setupUpdateNIKUser(textValue.toString())
                }

                KEY.KEY_TITLE_PHONE_NUMBER_NAVIGATION_PROFILE_TO_UPDATE_PROFILE -> {
                    setupUpdatePhoneNumberUser(textValue.toString())
                }

                KEY.KEY_TITLE_ADDRESS_NAVIGATION_PROFILE_TO_UPDATE_PROFILE -> {
                    setupUpdateAddressUser(textValue.toString())
                }
            }
        }
    }

    private fun setupNavigation() {
        binding.viewAppbar.buttonBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setupUpdateAddressUser(address: String) {
        viewModel.updateAddressUser(token, address).observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                Status.LOADING -> {}
                Status.SUCCESS -> {
                    UiHandler.toastSuccessMessage(
                        requireContext(), resource.data?.message.toString()
                    )
                    findNavController().popBackStack()
                }

                Status.ERROR -> {
                    UiHandler.toastErrorMessage(requireContext(), resource.message.toString())
                }
            }
        }
    }

    private fun setupUpdateNameUser(name: String) {
        viewModel.updateNameUser(token, name).observe(viewLifecycleOwner) { resources ->
            when (resources.status) {
                Status.LOADING -> {}
                Status.SUCCESS -> {
                    UiHandler.toastSuccessMessage(
                        requireContext(), resources.data?.message.toString()
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