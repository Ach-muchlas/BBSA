package com.am.bbsa.ui.bottom_sheet

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.FragmentManager
import com.am.bbsa.databinding.FragmentChooseCameraOrGaleriBottomSheetBinding
import com.am.bbsa.utils.Formatter
import com.am.bbsa.utils.UiHandler
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ChooseGalleryOrCamera2BottomSheet (private val callbackImageUri: (Uri) -> Unit): BottomSheetDialogFragment() {
    private var _binding: FragmentChooseCameraOrGaleriBottomSheetBinding? = null
    private val binding get() = _binding!!

    private lateinit var currentImageUri: Uri

    private val launcherIntentCamera: ActivityResultLauncher<Uri> = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { isSuccess ->
        if (isSuccess) {
            callbackImageUri.invoke(currentImageUri)
            dismiss()
        } else {
            UiHandler.toastErrorMessage(requireContext(), "Failed to take picture")
        }
    }

    private val launcherIntentGallery: ActivityResultLauncher<String> = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            callbackImageUri.invoke(it)
            dismiss()
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =
            FragmentChooseCameraOrGaleriBottomSheetBinding.inflate(inflater, container, false)
        setupNavigation()
        return binding.root
    }

    private fun setupNavigation(){
        binding.buttonClose.setOnClickListener {
            dismiss()
        }
        with(binding.cardChooseOption) {
            cardGallery.setOnClickListener {
                pickImageGallery()
            }
            cardCamera.setOnClickListener {
                startCamera()
            }
        }
    }

    private fun startCamera() {
        currentImageUri = Formatter.getImageUri(requireContext())
        launcherIntentCamera.launch(currentImageUri)
    }

    private fun pickImageGallery() {
        launcherIntentGallery.launch("image/*")
    }

    companion object {
        fun show(fragmentManager: FragmentManager, callbackImageUri: (Uri) -> Unit) {
            val bottomSheetFilter = ChooseGalleryOrCamera2BottomSheet(callbackImageUri)
            bottomSheetFilter.show(fragmentManager, bottomSheetFilter.tag)
        }
    }
}