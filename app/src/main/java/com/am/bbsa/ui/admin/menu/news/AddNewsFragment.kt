package com.am.bbsa.ui.admin.menu.news

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.am.bbsa.databinding.FragmentAddOrUpdateNewsBinding
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

class AddNewsFragment : Fragment() {
    private var _binding: FragmentAddOrUpdateNewsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MenuViewModel by inject()
    private val authViewModel: AuthViewModel by inject()

    /*instantiation of firebase storage object to send images*/
    private lateinit var storage: FirebaseStorage
    private lateinit var storageReference: StorageReference
    private lateinit var firebaseFirestore: FirebaseFirestore

    /*instantiation of the object to store the image uri*/
    private var imageUri: Uri? = null
    private lateinit var imageUrl: String

    private val token by lazy {
        authViewModel.getCredentialUser()?.token.toString()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddOrUpdateNewsBinding.inflate(inflater, container, false)
        setupNavigation()
        initVars()
        setupView()
        return binding.root
    }

    /*intent to object foto*/
    private val resultLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) {
        imageUri = it
        binding.imageNews.setImageURI(it)
    }

    private fun initVars() {
        /*initialize object firebase*/
        storage = Firebase.storage
        storageReference = storage.reference
        firebaseFirestore = FirebaseFirestore.getInstance()
    }

    private fun setupView() {
        UiHandler.setupVisibilityBottomNavigationAdmin(activity, true)
    }

    private fun setupNavigation() {
        binding.viewAppbar.buttonBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.cardPhoto.setOnClickListener {
            resultLauncher.launch("image/*")
        }
        binding.buttonSave.setOnClickListener {
            uploadImageToFirebaseAndPostApiForDatabase()
        }
    }

    //When this function runs it will send the image to firebase
    //after that it will download the url and enter it into the view model
    private fun uploadImageToFirebaseAndPostApiForDatabase() {
        imageUri?.let { filePath ->
            val ref = storageReference.child("Images/Sampah/${UUID.randomUUID()}")

            ref.putFile(filePath).addOnSuccessListener {
                UiHandler.toastSuccessMessage(
                    requireContext(),
                    "Berhasil Mengirim foto ke Firebase!!"
                )
                ref.downloadUrl.addOnSuccessListener { url ->
                    imageUrl = url.toString()
                    /*pada fungsi ini akan mengirimkan data ke dalam database*/
                    setupPostDataToApi(imageUrl)
                }
            }.addOnFailureListener { e ->
                UiHandler.toastErrorMessage(requireContext(), "task : ${e.message}")
            }
        }
    }

    private fun setupPostDataToApi(imageUrl: String) {
        val title = binding.edtTitle.text.toString()
        val desc = binding.edtDescription.text.toString()

        viewModel.createNews(token, title, desc, imageUrl).observe(viewLifecycleOwner) { resource ->
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