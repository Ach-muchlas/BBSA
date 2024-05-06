package com.am.bbsa.ui.admin.menu.nasabah

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.am.bbsa.data.response.DataItemDetailNasabah
import com.am.bbsa.databinding.FragmentDetailNasabahBinding
import com.am.bbsa.service.source.Status
import com.am.bbsa.ui.admin.menu.MenuViewModel
import com.am.bbsa.ui.auth.AuthViewModel
import com.am.bbsa.ui.bottom_sheet.ChooseGenderBottomSheet
import com.am.bbsa.utils.Destination
import com.am.bbsa.utils.Formatter
import com.am.bbsa.utils.Navigation
import com.am.bbsa.utils.UiHandler
import com.bumptech.glide.Glide
import org.koin.android.ext.android.inject

class DetailNasabahFragment : Fragment() {
    private var _binding: FragmentDetailNasabahBinding? = null
    private val binding get() = _binding!!
    private val authViewModel: AuthViewModel by inject()
    private val viewModel: MenuViewModel by inject()

    private val token by lazy { authViewModel.getCredentialUser()?.token.toString() }
    private val receiveArgsIdNasabah: Int by lazy {
        arguments?.getInt(NasabahFragment.KEY_NASABAH_ID) ?: 0
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailNasabahBinding.inflate(inflater, container, false)
        initialize()
        setupGetDataDetailProfileNasabahFromApi()
        return binding.root
    }

    private fun initialize() {
        UiHandler.setupVisibilityBottomNavigationAdmin(activity, true)
        binding.viewAppbar.buttonBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setupGetDataDetailProfileNasabahFromApi() {
        viewModel.showDetailNasabahById(receiveArgsIdNasabah, token)
            .observe(viewLifecycleOwner) { resource ->
                when (resource.status) {
                    Status.LOADING -> {
                        showShimmer(true)
                    }

                    Status.SUCCESS -> {
                        showShimmer(false)
                        setupIsVisibilityView()
                        setupViewCredentialUser(resource.data?.data)
                        setupNavigationToEditFragment(resource.data?.data)
                    }

                    Status.ERROR -> {
                        showShimmer(false)
                        UiHandler.toastErrorMessage(requireContext(), resource.message.toString())
                    }
                }
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
        with(binding.cardStatusNasabah) {
            textValueStatus.text = data?.status
        }
    }

    private fun setupNavigationToEditFragment(data: DataItemDetailNasabah?) {
        data?.let { nasabah ->
            binding.imageProfile.setOnClickListener {
                val bundle = Bundle().apply {
                    putInt(KEY_BUNDLE_ID_NASABAH, nasabah.id)
                    putString(KEY_BUNDLE_VALUE, nasabah.user?.photoProfile.toString())
                }
                Navigation.navigationFragment(
                    Destination.DETAIL_NASABAH_TO_UPDATE_PHOTO_PROFILE,
                    findNavController(),
                    bundle
                )
            }
            with(binding.cardPersonalInformation) {
                cardName.setOnClickListener {
                    setupCredentialNavigateToEditFragment(
                        nasabah.id,
                        KEY_NAME,
                        nasabah.user?.name.toString()
                    )
                }
                cardNIK.setOnClickListener {
                    setupCredentialNavigateToEditFragment(
                        nasabah.id,
                        KEY_NIK,
                        nasabah.NIK.toString()
                    )
                }
                cardNumberPhone.setOnClickListener {
                    setupCredentialNavigateToEditFragment(
                        nasabah.id,
                        KEY_PHONE_NUMBER,
                        nasabah.user?.phoneNumber.toString()
                    )
                }
                cardNumberRegis.setOnClickListener {
                    setupCredentialNavigateToEditFragment(
                        nasabah.id,
                        KEY_NO_REGIS,
                        nasabah.noRegis.toString()
                    )
                }
            }

            with(binding.cardPersonalIdentity) {
                cardGender.setOnClickListener {
                    val bottomSheet = ChooseGenderBottomSheet()
                    bottomSheet.show(childFragmentManager, bottomSheet.tag)
                }
                cardAddress.setOnClickListener {
                    setupCredentialNavigateToEditFragment(
                        nasabah.id,
                        KEY_ADDRESS,
                        nasabah.user?.address.toString()
                    )
                }
            }
        }
    }

    private fun setupCredentialNavigateToEditFragment(
        bundleIdNasabah: Int,
        bundleTitle: String,
        bundleValueContent: String
    ) {
        val bundle = Bundle().apply {
            putInt(KEY_BUNDLE_ID_NASABAH, bundleIdNasabah)
            putString(KEY_BUNDLE, bundleTitle)
            putString(KEY_BUNDLE_VALUE, bundleValueContent)
        }
        Navigation.navigationFragment(
            Destination.DETAIL_NASABAH_TO_UPDATE_NASABAH, findNavController(), bundle
        )
    }

    private fun showShimmer(isVisible: Boolean) {
        UiHandler.manageShimmer(binding.shimmerContainerImageProfile, isVisible)
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
        with(binding.cardStatusNasabah) {
            UiHandler.manageShimmer(shimmerContainerStatus, isVisible)
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
        with(binding.cardPersonalBalance) {
            textValueBalance.visibility = View.VISIBLE
            textValuePredict.visibility = View.VISIBLE
        }
        with(binding.cardStatusNasabah) {
            textValueStatus.visibility = View.VISIBLE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val KEY_BUNDLE = "bundle"
        const val KEY_BUNDLE_VALUE = "value"
        const val KEY_BUNDLE_ID_NASABAH = "id_nasabah"
        const val KEY_NAME = "Nama"
        const val KEY_NIK = "NIK"
        const val KEY_PHONE_NUMBER = "Nomor Telephone"
        const val KEY_ADDRESS = "Alamat"
        const val KEY_NO_REGIS = "Nomor Registrasi"
    }

}