package com.am.bbsa.ui.admin.account.change_password

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
        return binding.root
    }


    private fun setupView() {
        UiHandler.setupVisibilityBottomNavigationAdmin(activity, true)
        binding.viewAppBar.textTitleAppBar.setText(R.string.change_password)
        UiHandler.setHintBehavior(
            binding.edlOldPassword,
            binding.edlNewPassword,
            binding.edlRepeatNewPassword
        )

    }
    private fun setupNavigation() {
        binding.viewAppBar.buttonBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.buttonChangePassword.setOnClickListener {
            changePasswordUser()
        }
    }

    private fun changePasswordUser() {
        val oldPassword = binding.edtOldPassword.text
        val newPassword = binding.edtNewPassword.text
        val repeatNewPassword = binding.edtRepeatPassword.text

        if (!UiHandler.validatePassword(newPassword.toString(), requireContext())) {
            return
        } else if (repeatNewPassword.toString() != newPassword.toString()) {
            UiHandler.toastErrorMessage(
                requireContext(),
                "Password baru dan konfirmasi ulangi password tidak cocok."
            )
            return
        }

        viewModel.changePassword(
            token, oldPassword.toString(), newPassword.toString(), repeatNewPassword.toString()
        ).observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                Status.LOADING -> {}
                Status.SUCCESS -> {
                    UiHandler.toastSuccessMessage(
                        requireContext(), resource.data?.message.toString()
                    )
                    clearFocusEdt()
                    findNavController().popBackStack()
                }

                Status.ERROR -> {
                    UiHandler.toastErrorMessage(
                        requireContext(), resource.message.toString()
                    )
                }
            }
        }
    }

    private fun clearFocusEdt() {
        binding.edtOldPassword.clearFocus()
        binding.edtNewPassword.clearFocus()
        binding.edtRepeatPassword.clearFocus()
        binding.edtOldPassword.setText("")
        binding.edtNewPassword.setText("")
        binding.edtRepeatPassword.setText("")
    }


    override fun onDestroy() {
        super.onDestroy()
        UiHandler.setupVisibilityBottomNavigationAdmin(activity, false)
    }
}