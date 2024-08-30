package com.am.bbsa.ui.bottom_sheet

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.am.bbsa.databinding.FragmentChooseCameraOrGaleriBottomSheetBinding
import com.am.bbsa.service.source.Status
import com.am.bbsa.ui.admin.menu.MenuViewModel
import com.am.bbsa.ui.admin.menu.nasabah.UpdatePhotoProfileFragment
import com.am.bbsa.ui.auth.AuthViewModel
import com.am.bbsa.ui.customers.account.AccountViewModel
import com.am.bbsa.utils.Formatter
import com.am.bbsa.utils.UiHandler
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.storage
import org.koin.android.ext.android.inject
import java.util.UUID

class ChooseGalleryOrCameraBottomSheet : BottomSheetDialogFragment() {
    private var _binding: FragmentChooseCameraOrGaleriBottomSheetBinding? = null
    private val binding get() = _binding!!

    /*instantiation of firebase storage object to send images*/
    private lateinit var storage: FirebaseStorage
    private lateinit var storageReference: StorageReference
    private lateinit var firebaseFirestore: FirebaseFirestore
    private lateinit var imageUrl: String

    private val viewModel: MenuViewModel by inject()
    private val authViewModel: AuthViewModel by inject()
    private val accountViewModel: AccountViewModel by inject()
    private val token: String by lazy { authViewModel.getCredentialUser()?.token.toString() }
    private val receiveArgsNasabahId: Int by lazy {
        arguments?.getInt(UpdatePhotoProfileFragment.KEY_BUNDLE_ID_NASABAH) ?: 0
    }

    private lateinit var currentImageUri: Uri

    private val launcherIntentCamera: ActivityResultLauncher<Uri> = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { isSuccess ->
        if (isSuccess) {
            uploadImageToFirebaseAndPostApiForDatabase(currentImageUri)
        } else {
            UiHandler.toastErrorMessage(requireContext(), "Failed to take picture")
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding =
            FragmentChooseCameraOrGaleriBottomSheetBinding.inflate(inflater, container, false)
        initialize()
        setupNavigation()
        return binding.root
    }

    private fun initialize() {
        /*inisialisasi object firebase*/
        storage = Firebase.storage
        storageReference = storage.reference
        firebaseFirestore = FirebaseFirestore.getInstance()
    }

    private fun setupNavigation() {
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
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            val imageUri = data?.data
            if (imageUri != null) {
                uploadImageToFirebaseAndPostApiForDatabase(imageUri)
            }
        }
    }

    private fun uploadImageToFirebaseAndPostApiForDatabase(imageUri: Uri?) {
        imageUri?.let { filePath ->
            val ref = storageReference.child("Images/Profile/${UUID.randomUUID()}")
            ref.putFile(filePath).addOnSuccessListener {

                ref.downloadUrl.addOnSuccessListener { url ->
                    imageUrl = url.toString()
                    setupPostDataToApi(imageUrl)
                }
            }.addOnFailureListener { e ->
                UiHandler.toastErrorMessage(requireContext(), "task : ${e.message}")
            }
        }
    }

    private fun setupPostDataToApi(imageUrl: String) {
        if (receiveArgsNasabahId != 0) {
            viewModel.changePhotoProfileNasabah(token, receiveArgsNasabahId, imageUrl)
                .observe(viewLifecycleOwner) { resource ->
                    when (resource.status) {
                        Status.LOADING -> {
                            UiHandler.toastSuccessMessage(requireContext(), "Loading ...")
                        }

                        Status.SUCCESS -> {
                            (parentFragment as UpdatePhotoProfileFragment).onImageProfileUpdated()
                            dismissNow()
                        }

                        Status.ERROR -> {
                            UiHandler.toastErrorMessage(
                                requireContext(), resource.message.toString()
                            )
                        }
                    }
                }
        } else {
            accountViewModel.updatePhotoProfileUser(token, imageUrl)
                .observe(viewLifecycleOwner) { resource ->
                    when (resource.status) {
                        Status.LOADING -> {
                            UiHandler.toastSuccessMessage(requireContext(), "Loading ...")
                        }

                        Status.SUCCESS -> {
                            (parentFragment as UpdatePhotoProfileFragment).onImageProfileUpdated()
                            dismissNow()
                        }

                        Status.ERROR -> {
                            UiHandler.toastErrorMessage(
                                requireContext(), resource.message.toString()
                            )
                        }
                    }
                }
        }
    }

    companion object {
        const val IMAGE_PICK_CODE = 1000
    }
}