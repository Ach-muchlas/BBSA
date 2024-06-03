package com.am.bbsa.ui.admin.menu.waste_type_information

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.am.bbsa.databinding.FragmentAddOrUpdateWasteTypeBinding
import com.am.bbsa.service.source.Status
import com.am.bbsa.ui.admin.menu.MenuViewModel
import com.am.bbsa.ui.auth.AuthViewModel
import com.am.bbsa.ui.bottom_sheet.ChooseGalleryOrCamera2BottomSheet
import com.am.bbsa.utils.UiHandler
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.storage
import org.koin.android.ext.android.inject
import java.util.UUID

class AddWasteTypeFragment : Fragment() {
    private var _binding: FragmentAddOrUpdateWasteTypeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MenuViewModel by inject()
    private val authViewModel: AuthViewModel by inject()

    private lateinit var storage: FirebaseStorage
    private lateinit var storageReference: StorageReference
    private lateinit var firebaseFirestore: FirebaseFirestore
    private lateinit var currentImageUri: Uri

    private val token by lazy {
        authViewModel.getCredentialUser()?.token.toString()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddOrUpdateWasteTypeBinding.inflate(inflater, container, false)
        setupNavigation()
        initVars()
        return binding.root
    }

    private fun setupNavigation() {
        binding.buttonSave.setOnClickListener {
            if (currentImageUri != null) {
                uploadImageToFirebase(currentImageUri)
            } else {
                setupPostDataToApi("foto")
            }
        }
        binding.cardPhoto.setOnClickListener {
            ChooseGalleryOrCamera2BottomSheet.show(childFragmentManager) { uri ->
                currentImageUri = uri
                binding.imageWaste.setImageURI(uri)
            }
        }
    }

    private fun uploadImageToFirebase(imageUri: Uri) {
        val ref = storageReference.child("Images/Sampah/${UUID.randomUUID()}")
        ref.putFile(imageUri).addOnSuccessListener {
            ref.downloadUrl.addOnSuccessListener { url ->
                val imageUrl = url.toString()
                setupPostDataToApi(imageUrl)
            }
        }.addOnFailureListener { e ->
            UiHandler.toastErrorMessage(requireContext(), "Upload failed: ${e.message}")
        }
    }

    private fun initVars() {
        storage = Firebase.storage
        storageReference = storage.reference
        firebaseFirestore = FirebaseFirestore.getInstance()
    }

    private fun setupPostDataToApi(imageUrl: String) {
        val name = binding.edtNameWaste.text.toString()
        val type = binding.edtTypeWaste.text.toString()
        val price = binding.edtPrice.text.toString().toInt()

        viewModel.createInformationWaste(token, name, type, price, imageUrl)
            .observe(viewLifecycleOwner) { resource ->
                when (resource.status) {
                    Status.LOADING -> {}
                    Status.SUCCESS -> {
                        findNavController().popBackStack()
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