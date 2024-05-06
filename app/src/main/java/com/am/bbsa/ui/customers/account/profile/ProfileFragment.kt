package com.am.bbsa.ui.customers.account.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.am.bbsa.data.response.DataItemDetailNasabah
import com.am.bbsa.databinding.FragmentProfileBinding
import com.am.bbsa.service.source.Status
import com.am.bbsa.ui.auth.AuthViewModel
import com.am.bbsa.ui.bottom_sheet.ChooseGenderBottomSheet
import com.am.bbsa.ui.customers.account.AccountViewModel
import com.am.bbsa.utils.Destination
import com.am.bbsa.utils.Formatter
import com.am.bbsa.utils.Navigation
import com.am.bbsa.utils.UiHandler
import com.bumptech.glide.Glide
import org.koin.android.ext.android.inject

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AccountViewModel by inject()
    private val authViewModel: AuthViewModel by inject()
    private val token: String by lazy {
        authViewModel.getCredentialUser()?.token.toString()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        setupGetCredential()
        setupNavigation()
        UiHandler.setupVisibilityBottomNavigationNasabah(activity, true)
        return binding.root
    }
    private fun setupNavigation() {
        // variable bundle untuk pengiriman sebuah data dri fragment ke fragment
        val bundle = Bundle()

        // Return to previous fragment
        binding.viewAppbar.buttonBack.setOnClickListener {
            findNavController().popBackStack()
        }

        // berpindah kehalaman update profile dengan ada nya data
        with(binding.cardPersonalInformation) {
            cardName.setOnClickListener {
                bundle.putString(KEY_TEXT_VIEW_BAR_UPDATE_PROFILE, "Nama")
                Navigation.navigationFragment(
                    Destination.PROFILE_TO_UPDATE_PROFILE, findNavController(), bundle
                )
            }
            cardNIK.setOnClickListener {
                bundle.putString(KEY_TEXT_VIEW_BAR_UPDATE_PROFILE, "NIK")
                Navigation.navigationFragment(
                    Destination.PROFILE_TO_UPDATE_PROFILE, findNavController(), bundle
                )
            }
            cardNumberPhone.setOnClickListener {
                bundle.putString(KEY_TEXT_VIEW_BAR_UPDATE_PROFILE, "Nomer Telephone")
                Navigation.navigationFragment(
                    Destination.PROFILE_TO_UPDATE_PROFILE, findNavController(), bundle
                )
            }
        }

        with(binding.cardPersonalIdentity) {
            cardAddress.setOnClickListener {
                bundle.putString(KEY_TEXT_VIEW_BAR_UPDATE_PROFILE, "Alamat")
                Navigation.navigationFragment(
                    Destination.PROFILE_TO_UPDATE_PROFILE, findNavController(), bundle
                )
            }
            cardGender.setOnClickListener {
                val bottomSheet = ChooseGenderBottomSheet()
                bottomSheet.show(childFragmentManager, bottomSheet.tag)
            }
        }
    }
    // function ini digunkaan untuk mengambil id user
    private fun setupGetCredential() {
        viewModel.showDataUser(token).observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                Status.LOADING -> {}

                Status.SUCCESS -> {
                    val userId = resource.data?.data?.nasabah?.id ?: 0
                    displayDetailProfile(userId)
                }

                Status.ERROR -> {
                    UiHandler.toastErrorMessage(requireContext(), resource.message.toString())
                }
            }
        }
    }

    private fun displayDetailProfile(id: Int) {
        viewModel.showDetailUser(id, token).observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                Status.LOADING -> {
                    showShimmer(true)
                }

                Status.SUCCESS -> {
                    showShimmer(false)
                    setupIsVisibilityView()
                    setupViewCredentialUser(resource.data?.data)
                }

                Status.ERROR -> {
                    showShimmer(true)
                    UiHandler.toastErrorMessage(requireContext(), resource.message.toString())
                }
            }
        }
    }

    private fun setupIsVisibilityView() {
        with(binding.cardPersonalIdentity) {
            textValueAddress.visibility = View.VISIBLE
            textValueGender.visibility = View.VISIBLE
        }
        with(binding.cardPersonalInformation) {
            textValueName.visibility = View.VISIBLE
            textValueNumberPhone.visibility = View.VISIBLE
            textValueNIK.visibility = View.VISIBLE
            textValueNumberRegis.visibility = View.VISIBLE
        }
        with(binding.cardPersonalBalance) {
            textValueBalance.visibility = View.VISIBLE
            textValuePredict.visibility = View.VISIBLE
        }
    }

    private fun setupViewCredentialUser(data: DataItemDetailNasabah?) {
        Glide.with(requireContext()).load(data?.user?.photoProfile).into(binding.imageProfile)
        with(binding.cardPersonalIdentity) {
            textValueAddress.text = data?.user?.address.toString()
            textValueGender.text = data?.user?.gender.toString()
        }
        with(binding.cardPersonalInformation) {
            textValueName.text = data?.user?.name
            textValueNumberPhone.text = data?.user?.phoneNumber
            textValueNIK.text = data?.NIK
            textValueNumberRegis.text = data?.noRegis
        }
        with(binding.cardPersonalBalance) {
            textValueBalance.text = Formatter.formatCurrency(data?.balance ?: 0)
            textValuePredict.text = Formatter.formatCurrency(data?.temporaryBalance ?: 0)
        }
    }

    private fun showShimmer(isVisible: Boolean) {
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
        with(binding.cardPersonalBalance) {
            UiHandler.manageShimmer(shimmerContainerBalance, isVisible)
            UiHandler.manageShimmer(shimmerContainerPredictBalance, isVisible)
        }
    }
    fun onGenderUpdated() {
        setupGetCredential()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        UiHandler.setupVisibilityBottomNavigationNasabah(activity, false)
    }

    companion object {
        const val KEY_TEXT_VIEW_BAR_UPDATE_PROFILE = "update"
    }
}