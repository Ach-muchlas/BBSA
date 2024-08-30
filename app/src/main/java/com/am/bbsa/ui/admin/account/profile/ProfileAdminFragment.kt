package com.am.bbsa.ui.admin.account.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.am.bbsa.R
import com.am.bbsa.data.response.DataItemUser
import com.am.bbsa.databinding.FragmentProfileBinding
import com.am.bbsa.service.source.Status
import com.am.bbsa.ui.admin.menu.nasabah.DetailNasabahFragment
import com.am.bbsa.ui.auth.AuthViewModel
import com.am.bbsa.ui.bottom_sheet.ChooseGenderBottomSheet
import com.am.bbsa.ui.customers.account.AccountViewModel
import com.am.bbsa.utils.Destination
import com.am.bbsa.utils.KEY
import com.am.bbsa.utils.Navigation
import com.am.bbsa.utils.UiHandler
import com.bumptech.glide.Glide
import org.koin.android.ext.android.inject

class ProfileAdminFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AccountViewModel by inject()
    private val authViewModel: AuthViewModel by inject()

    private val token: String by lazy { authViewModel.getCredentialUser()?.token.toString() }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        initialize()
        setupGetCredential()
        return binding.root
    }

    private fun initialize() {
        UiHandler.setupVisibilityBottomNavigationAdmin(activity, true)
        binding.cardPersonalBalance.root.visibility = View.GONE
        binding.cardPersonalInformation.cardNumberRegis.visibility = View.GONE
        binding.cardPersonalInformation.cardNIK.visibility = View.GONE
    }

    private fun setupDataNavigationAndMoveToFragmentUpdateProfile(
        keyValueBundle: String,
        valueContentBundle: String
    ) {
        val bundle = Bundle().apply {
            putString(KEY.BUNDLE_KEY, keyValueBundle)
            putString(KEY.BUNDLE_KEY_VALUE, valueContentBundle)
        }
        Navigation.navigationFragment(
            Destination.PROFILE_ADMIN_TO_UPDATE_PROFILE,
            findNavController(),
            bundle
        )
    }

    private fun setupNavigation(data: DataItemUser?) {
        binding.viewAppbar.buttonBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.imageProfile.setOnClickListener {
            val bundle = Bundle().apply {
                putInt(DetailNasabahFragment.KEY_BUNDLE_ID_NASABAH, 0)
                putString(
                    DetailNasabahFragment.KEY_BUNDLE_VALUE,
                    data?.fotoProfil.toString()
                )
            }
            Navigation.navigationFragment(
                Destination.PROFILE_ADMIN_T0_UPDATE_PHOTO_PROFILE,
                findNavController(),
                bundle
            )
        }

        with(binding.cardPersonalInformation) {
            cardName.setOnClickListener {
                setupDataNavigationAndMoveToFragmentUpdateProfile(
                    KEY.KEY_TITLE_NAME_NAVIGATION_PROFILE_TO_UPDATE_PROFILE,
                    data?.name.toString()
                )
            }
            cardNumberPhone.setOnClickListener {
                setupDataNavigationAndMoveToFragmentUpdateProfile(
                    KEY.KEY_TITLE_PHONE_NUMBER_NAVIGATION_PROFILE_TO_UPDATE_PROFILE,
                    data?.nomorTelephone.toString()
                )
            }
        }

        with(binding.cardPersonalIdentity) {
            cardAddress.setOnClickListener {
                setupDataNavigationAndMoveToFragmentUpdateProfile(
                    KEY.KEY_TITLE_ADDRESS_NAVIGATION_PROFILE_TO_UPDATE_PROFILE,
                    data?.alamat.toString()
                )
            }
            cardGender.setOnClickListener {
                val bottomSheet = ChooseGenderBottomSheet(false, 0)
                bottomSheet.show(childFragmentManager, bottomSheet.tag)
            }
        }
    }


    fun onGenderUpdated() {
        setupGetCredential()
    }

    private fun setupGetCredential() {
        viewModel.showDataUser(token).observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                Status.LOADING -> {
                    showShimmer(true)
                }

                Status.SUCCESS -> {
                    showShimmer(false)
                    setupIsVisibilityView()
                    setupViewCredentialUser(resource.data?.data)
                    setupNavigation(resource.data?.data)
                }

                Status.ERROR -> {
                    showShimmer(true)
                    UiHandler.toastErrorMessage(requireContext(), resource.message.toString())
                }
            }
        }
    }

    private fun setupIsVisibilityView() {
        binding.imageProfile.visibility = View.VISIBLE
        with(binding.cardPersonalIdentity) {
            textValueAddress.visibility = View.VISIBLE
            textValueGender.visibility = View.VISIBLE
        }
        with(binding.cardPersonalInformation) {
            textValueName.visibility = View.VISIBLE
            textValueNIK.visibility = View.VISIBLE
            textValueNumberPhone.visibility = View.VISIBLE
            textValueNumberRegis.visibility = View.VISIBLE
        }
    }


    private fun setupViewCredentialUser(data: DataItemUser?) {
        if (data?.fotoProfil.isNullOrEmpty()) {
            when (data?.jenisKelamin) {
                "Perempuan" -> binding.imageProfile.setImageResource(R.drawable.icon_profile_women)
                else -> binding.imageProfile.setImageResource(R.drawable.icon_profile_man)
            }
        } else {
            Glide.with(requireContext()).load(data?.fotoProfil)
                .into(binding.imageProfile)
        }

        with(binding.cardPersonalIdentity) {
            textValueAddress.text = data?.alamat.toString()
            textValueGender.text = data?.jenisKelamin.toString()
        }
        with(binding.cardPersonalInformation) {
            textValueName.text = data?.name
            textValueNumberPhone.text = data?.nomorTelephone
        }
    }

    private fun showShimmer(isVisible: Boolean) {
        UiHandler.manageShimmer(binding.shimmerContainerProfile, isVisible)
        with(binding.cardPersonalIdentity) {
            UiHandler.manageShimmer(shimmerContainerAddress, isVisible)
            UiHandler.manageShimmer(shimmerContainerGender, isVisible)
        }
        with(binding.cardPersonalInformation) {
            UiHandler.manageShimmer(shimmerContainerName, isVisible)
            UiHandler.manageShimmer(shimmerContainerNIK, isVisible)
            UiHandler.manageShimmer(shimmerContainerNumberRegis, isVisible)
            UiHandler.manageShimmer(shimmerContainerPhoneNumber, isVisible)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        UiHandler.setupVisibilityBottomNavigationAdmin(activity, false)
    }
}