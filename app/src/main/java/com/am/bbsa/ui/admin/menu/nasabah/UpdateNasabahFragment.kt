package com.am.bbsa.ui.admin.menu.nasabah

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.am.bbsa.R
import com.am.bbsa.databinding.FragmentUpdateProfileBinding
import com.am.bbsa.service.source.Status
import com.am.bbsa.ui.admin.menu.MenuViewModel
import com.am.bbsa.ui.auth.AuthViewModel
import com.am.bbsa.utils.UiHandler
import org.koin.android.ext.android.inject

class UpdateNasabahFragment : Fragment() {
    private var _binding: FragmentUpdateProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MenuViewModel by inject()
    private val authViewModel: AuthViewModel by inject()

    private val token: String by lazy { authViewModel.getCredentialUser()?.token.toString() }
    private val receiveArgsTitle: String by lazy {
        arguments?.getString(DetailNasabahFragment.KEY_BUNDLE).toString()
    }
    private val receiveArgsValueContent: String by lazy {
        arguments?.getString(
            DetailNasabahFragment.KEY_BUNDLE_VALUE
        ).toString()
    }
    private val receiveArgsIdNasabah: Int by lazy {
        arguments?.getInt(DetailNasabahFragment.KEY_BUNDLE_ID_NASABAH) ?: 0
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpdateProfileBinding.inflate(inflater, container, false)
        initialize()
        setupNavigation()
        setupPostEditDataProfileToApi()
        return binding.root
    }

    private fun initialize() {
        UiHandler.setupVisibilityBottomNavigationNasabah(activity, true)
        if (receiveArgsValueContent == "null") {
            binding.edtText.setText("")
            binding.edtText.hint = getString(R.string.input_your_data)
        } else {
            binding.edtText.setText(receiveArgsValueContent)
        }
    }

    private fun setupPostEditDataProfileToApi() {
        val textValue = binding.edtText.text
        binding.viewAppbar.textTitleAppBar.text = buildString {
            append("Edit ")
            append(receiveArgsTitle)
        }
        binding.text.text = receiveArgsTitle

        binding.buttonSave.setOnClickListener {
            when (receiveArgsTitle) {
                DetailNasabahFragment.KEY_NAME -> setupChangeNameNasabah(textValue.toString())

                DetailNasabahFragment.KEY_NIK -> {
                    setupChangeNIKNasabah(textValue.toString())
                }

                DetailNasabahFragment.KEY_PHONE_NUMBER -> {
                    setupChangePhoneNumberNasabah(textValue.toString())
                }

                DetailNasabahFragment.KEY_ADDRESS -> {
                    setupChangeAddressNasabah(textValue.toString())
                }

                DetailNasabahFragment.KEY_NO_REGIS -> {
                    setupChangeNoRegisterNasabah(textValue.toString())
                }
            }
        }
    }

    private fun setupNavigation() {
        binding.viewAppbar.buttonBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setupChangeNameNasabah(name: String) {
        viewModel.changeNameNasabah(token, receiveArgsIdNasabah, name)
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

    private fun setupChangeNoRegisterNasabah(noRegister: String) {
        viewModel.changeNoRegisterNasabah(token, receiveArgsIdNasabah, noRegister)
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

    private fun setupChangeNIKNasabah(NIK: String) {
        viewModel.changeNIKNasabah(token, receiveArgsIdNasabah, NIK)
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

    private fun setupChangePhoneNumberNasabah(phoneNumber: String) {
        viewModel.changePhoneNumberNasabah(token, receiveArgsIdNasabah, phoneNumber)
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

    private fun setupChangeAddressNasabah(address: String) {
        viewModel.changeAddressNasabah(token, receiveArgsIdNasabah, address)
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