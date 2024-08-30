package com.am.bbsa.ui.customers.home.pick_up_waste

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.am.bbsa.R
import com.am.bbsa.databinding.FragmentRegisterWastePickUpBinding
import com.am.bbsa.service.source.Status
import com.am.bbsa.ui.auth.AuthViewModel
import com.am.bbsa.ui.bottom_sheet.ChooseGalleryOrCamera2BottomSheet
import com.am.bbsa.ui.customers.home.HomeViewModel
import com.am.bbsa.utils.UiHandler
import com.bumptech.glide.Glide
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.storage
import org.koin.android.ext.android.inject
import java.util.UUID

class RegisterWastePickUpFragment : Fragment() {
    private var _binding: FragmentRegisterWastePickUpBinding? = null
    private val binding get() = _binding!!
    private val authViewModel: AuthViewModel by inject()
    private val viewModel: HomeViewModel by inject()

    private val token: String by lazy { authViewModel.getCredentialUser()?.token.toString() }
    private val receiveIdPickupWaste: Int by lazy {
        arguments?.getInt(PickUpWasteFragment.BUNDLE_id) ?: 0
    }

    /*instantiation of firebase storage object to send images*/
    private lateinit var storage: FirebaseStorage
    private lateinit var storageReference: StorageReference
    private lateinit var firebaseFirestore: FirebaseFirestore

    /*instantiation of the object to store the image uri*/
    private var currentImageUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterWastePickUpBinding.inflate(inflater, container, false)
        initVars()
        initialize()
        setupNavigation()
        return binding.root
    }

    private fun initialize() {
        UiHandler.setupVisibilityBottomNavigationNasabah(activity, true)
        UiHandler.setHintBehavior(binding.edlDescription)
        binding.viewAppBar.textTitleAppBar.text = getString(R.string.register_pick_up)
    }

    private fun initVars() {
        /*initialize object firebase*/
        storage = Firebase.storage
        storageReference = storage.reference
        firebaseFirestore = FirebaseFirestore.getInstance()
    }

    private fun setupNavigation() {
        binding.viewAppBar.buttonBack.setOnClickListener { findNavController().popBackStack() }
        binding.buttonSave.setOnClickListener {
            if (currentImageUri != null) {
                uploadImageToFirebase(currentImageUri!!)
            } else {
                UiHandler.toastErrorMessage(requireContext(), "Mohon masukan foto terlebih dahulu")
            }
        }

        binding.cardValuePhoto.setOnClickListener {
            ChooseGalleryOrCamera2BottomSheet.show(childFragmentManager) { uri ->
                currentImageUri = uri
                Glide.with(requireContext()).load(uri).into(binding.imageWaste)
            }
        }
    }

    private fun uploadImageToFirebase(imageUri: Uri) {
        val ref = storageReference.child("Images/Jemput Sampah/${UUID.randomUUID()}")
        ref.putFile(imageUri).addOnSuccessListener {
            ref.downloadUrl.addOnSuccessListener { url ->
                val imageUrl = url.toString()
                setupPostDataToApi(imageUrl)
            }
        }.addOnFailureListener { e ->
            UiHandler.toastErrorMessage(requireContext(), "Upload failed: ${e.message}")
        }
    }

    private fun setupPostDataToApi(imageUrl: String) {
        val description = binding.edtDescription.text
        viewModel.createRegistrantNasabahPickUpWaste(
            token,
            receiveIdPickupWaste,
            description.toString(),
            imageUrl
        ).observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                Status.LOADING -> {}

                Status.SUCCESS -> {
                    UiHandler.toastSuccessMessage(
                        requireContext(),
                        resource.data?.message.toString()
                    )
                    findNavController().popBackStack()
                }

                Status.ERROR -> {
                    UiHandler.toastErrorMessage(requireContext(), resource.message.toString())
                }
            }
        }
    }
}