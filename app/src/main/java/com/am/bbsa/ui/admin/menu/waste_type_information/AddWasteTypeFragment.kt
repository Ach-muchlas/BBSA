package com.am.bbsa.ui.admin.menu.waste_type_information

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.am.bbsa.databinding.FragmentAddOrUpdateWasteTypeBinding
import com.am.bbsa.service.source.Status
import com.am.bbsa.ui.admin.menu.MenuViewModel
import com.am.bbsa.ui.auth.AuthViewModel
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
    private var imageUri: Uri? = null
    private lateinit var imageUrl: String

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
            uploadImage()
        }
        binding.cardPhoto.setOnClickListener {
            resultLauncher.launch("image/*")
        }
    }

    private fun uploadImage() {
        imageUri?.let { filePath ->
            val ref = storageReference.child("Images/Sampah/${UUID.randomUUID()}")

            ref.putFile(filePath).addOnSuccessListener {
                UiHandler.toastSuccessMessage(requireContext(), "Image Success Uploaded!!")
                ref.downloadUrl.addOnSuccessListener { url ->
                    imageUrl = url.toString()
                    setupPostDataToApi(imageUrl)
                }
            }.addOnFailureListener { e ->
                UiHandler.toastErrorMessage(requireContext(), "task : ${e.message}")
            }
        }
    }

    private val resultLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) {
        imageUri = it
        binding.imageWaste.setImageURI(it)
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
                            resource.data?.message.toString()
                        )
                    }
                }
            }
    }
}