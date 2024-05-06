package com.am.bbsa.ui.admin.account.change_password

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.am.bbsa.R
import com.am.bbsa.databinding.FragmentChangePasswordBinding
import com.am.bbsa.service.source.Status
import com.am.bbsa.ui.auth.AuthViewModel
import com.am.bbsa.ui.customers.account.AccountViewModel
import com.am.bbsa.utils.UiHandler
import org.koin.android.ext.android.inject

class ChangePasswordAdminFragment : Fragment() {
    private var _binding: FragmentChangePasswordBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AccountViewModel by inject()
    private val authViewModel: AuthViewModel by inject()

    private val token: String by lazy {
        authViewModel.getCredentialUser()?.token.toString()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChangePasswordBinding.inflate(inflater, container, false)
        setupView()
        setupNavigation()
        changePasswordUser()
        return binding.root
    }

    private fun setupView() {
        binding.viewAppBar.textTitleAppBar.setText(R.string.change_password)
    }

    private fun setupNavigation() {
        binding.viewAppBar.buttonBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun changePasswordUser() {
        val oldPassword = binding.edtOldPassword.text
        val newPassword = binding.edtNewPassword.text
        val repeatNewPassword = binding.edtRepeatPassword.text
        binding.buttonChangePassword.setOnClickListener {
            viewModel.changePassword(
                token,
                oldPassword.toString(),
                newPassword.toString(),
                repeatNewPassword.toString()
            )
                .observe(viewLifecycleOwner) { resource ->
                    when (resource.status) {
                        Status.LOADING -> {}
                        Status.SUCCESS -> {
                            findNavController().popBackStack()
                            UiHandler.toastSuccessMessage(
                                requireContext(),
                                resource.message.toString()
                            )
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
    }

}