package com.am.bbsa.ui.admin.menu.nasabah

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.am.bbsa.databinding.FragmentUpdatePhotoProfileBinding
import com.am.bbsa.service.source.Status
import com.am.bbsa.ui.admin.menu.MenuViewModel
import com.am.bbsa.ui.auth.AuthViewModel
import com.am.bbsa.ui.bottom_sheet.ChooseGalleryOrCameraBottomSheet
import com.bumptech.glide.Glide
import org.koin.android.ext.android.inject

class UpdatePhotoProfileFragment : Fragment() {
    private var _binding: FragmentUpdatePhotoProfileBinding? = null
    private val binding get() = _binding!!
    private var imageUrl: String? = null

    private val bottomSheetChooseCameraOrGallery = ChooseGalleryOrCameraBottomSheet()
    private val authViewModel: AuthViewModel by inject()
    private val viewModel: MenuViewModel by inject()
    private val token: String by lazy { authViewModel.getCredentialUser()?.token.toString() }
    private val receiveArgsNasabahId: Int by lazy {
        arguments?.getInt(DetailNasabahFragment.KEY_BUNDLE_ID_NASABAH) ?: 0
    }
    private val receiveArgsValueImagePhotoProfile: String by lazy {
        arguments?.getString(
            DetailNasabahFragment.KEY_BUNDLE_VALUE
        ).toString()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpdatePhotoProfileBinding.inflate(inflater, container, false)
        setupNavigation()
        setupView()
        return binding.root
    }

    private fun setupView() {
        Glide.with(requireContext()).load(imageUrl ?: receiveArgsValueImagePhotoProfile)
            .into(binding.imageProfile)
    }

    private fun setupGetDataDetailNasabahFromApi() {
        viewModel.showDetailNasabahById(receiveArgsNasabahId, token).observe(viewLifecycleOwner){
            when(it.status){
                Status.LOADING -> {}
                Status.ERROR -> {}
                Status.SUCCESS ->{
                    imageUrl = it.data?.data?.user?.photoProfile.toString()
                    setupView()
                }
            }

        }
    }

    private fun setupNavigation() {
        binding.viewAppbar.buttonBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.viewAppbar.buttonEdit.setOnClickListener {
            val bundle = Bundle().apply {
                putInt(KEY_BUNDLE_ID_NASABAH, receiveArgsNasabahId)
            }
            bottomSheetChooseCameraOrGallery.arguments = bundle
            bottomSheetChooseCameraOrGallery.show(
                childFragmentManager, bottomSheetChooseCameraOrGallery.tag
            )
        }
    }

    fun onImageProfileUpdated(){
        setupGetDataDetailNasabahFromApi()
    }

    companion object {
        const val KEY_BUNDLE_ID_NASABAH = "id_nasabah"
    }
}