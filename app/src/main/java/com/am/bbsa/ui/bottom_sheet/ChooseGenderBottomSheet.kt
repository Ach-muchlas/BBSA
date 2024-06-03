package com.am.bbsa.ui.bottom_sheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.am.bbsa.databinding.FragmentBottomSheetChooseGenderBinding
import com.am.bbsa.service.source.Status
import com.am.bbsa.ui.admin.account.profile.ProfileAdminFragment
import com.am.bbsa.ui.admin.menu.MenuViewModel
import com.am.bbsa.ui.admin.menu.nasabah.DetailNasabahFragment
import com.am.bbsa.ui.auth.AuthViewModel
import com.am.bbsa.ui.customers.account.AccountViewModel
import com.am.bbsa.ui.customers.account.profile.ProfileFragment
import com.am.bbsa.utils.UiHandler
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.android.ext.android.inject

class ChooseGenderBottomSheet(private val isNasabah: Boolean, private val nasabahId: Int) :
    BottomSheetDialogFragment() {
    private var _binding: FragmentBottomSheetChooseGenderBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AccountViewModel by inject()
    private val menuViewModel: MenuViewModel by inject()
    private val authViewModel: AuthViewModel by inject()
    private val token: String by lazy {
        authViewModel.getCredentialUser()?.token.toString()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBottomSheetChooseGenderBinding.inflate(inflater, container, false)
        setupNavigation()
        return binding.root
    }

    private fun setupNavigation(){
        binding.textDone.setOnClickListener {
            dismiss()
        }
        binding.cardMen.setOnClickListener {
            val textMen = "Laki-Laki"
            if (!isNasabah) {
                changeGender(textMen)
                changeGenderAdmin(textMen)
            } else {
                changeGenderNasabah(textMen)
            }

        }
        binding.carWomen.setOnClickListener {
            val textWomen = "Perempuan"
            if (!isNasabah) {
                changeGender(textWomen)
                changeGenderAdmin(textWomen)
            } else {
                changeGenderNasabah(textWomen)
            }
        }
    }

    private fun changeGenderNasabah(gender: String) {
        menuViewModel.changeGenderNasabah(token, nasabahId, gender)
            .observe(viewLifecycleOwner) { resource ->
                when (resource.status) {
                    Status.LOADING -> {}
                    Status.SUCCESS -> {
                        (parentFragment as DetailNasabahFragment).onGenderUpdated()
                        dismiss()
                        UiHandler.toastSuccessMessage(requireContext(), "Berhasil")
                    }

                    Status.ERROR -> {
                        UiHandler.toastErrorMessage(requireContext(), resource.message.toString())
                    }
                }
            }
    }

    private fun changeGender(gender: String) {
        viewModel.updateGenderUser(token, gender).observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                Status.LOADING -> {}
                Status.SUCCESS -> {
                    (parentFragment as ProfileFragment).onGenderUpdated()
                    dismiss()
                    UiHandler.toastSuccessMessage(requireContext(), "Berhasil")
                }

                Status.ERROR -> {
                    UiHandler.toastErrorMessage(requireContext(), resource.message.toString())
                }
            }
        }
    }

    private fun changeGenderAdmin(gender: String) {
        viewModel.updateGenderUser(token, gender).observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                Status.LOADING -> {}
                Status.SUCCESS -> {
                    (parentFragment as ProfileAdminFragment).onGenderUpdated()
                    dismiss()
                    UiHandler.toastSuccessMessage(requireContext(), "Berhasil")
                }

                Status.ERROR -> {
                    UiHandler.toastErrorMessage(requireContext(), resource.message.toString())
                }
            }
        }
    }
}