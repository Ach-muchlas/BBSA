package com.am.bbsa.ui.admin.menu.news

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.am.bbsa.databinding.FragmentAddOrUpdateNewsBinding
import com.am.bbsa.service.source.Status
import com.am.bbsa.ui.admin.menu.MenuViewModel
import com.am.bbsa.ui.auth.AuthViewModel
import com.am.bbsa.ui.bottom_sheet.ChooseGalleryOrCamera2BottomSheet
import com.am.bbsa.utils.UiHandler
import com.bumptech.glide.Glide
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
    private var currentImageUri: Uri? = null

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

    private fun initVars() {
        /*initialize object firebase*/
        storage = Firebase.storage
        storageReference = storage.reference
        firebaseFirestore = FirebaseFirestore.getInstance()
    }

    private fun setupView() {
        UiHandler.setupVisibilityBottomNavigationAdmin(activity, true)
        binding.viewAppbar.textTitleAppBar.text = "Tambah berita"
        UiHandler.setHintBehavior(binding.edlTitle, binding.edlDescription)
    }

    private fun setupNavigation() {
        binding.viewAppbar.buttonBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.cardPhoto.setOnClickListener {
            ChooseGalleryOrCamera2BottomSheet.show(childFragmentManager) { uri ->
                currentImageUri = uri
                Glide.with(requireContext()).load(uri).into(binding.imageNews)
            }
        }
        binding.buttonSave.setOnClickListener {
            if (currentImageUri != null) {
                uploadImageToFirebase(currentImageUri!!)
            } else {
                setupPostDataToApi("photo")
            }
        }
    }

    //When this function runs it will send the image to firebase
    //after that it will download the url and enter it into the view model
    private fun uploadImageToFirebase(imageUri: Uri) {
        val ref = storageReference.child("Images/Berita/${UUID.randomUUID()}")
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
        val title = binding.edtTitle.text.toString()
        val desc = binding.edtDescription.text.toString()

        viewModel.createNews(token, title, desc, imageUrl).observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                Status.LOADING -> {
                    binding.textLoading.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.VISIBLE
                }

                Status.SUCCESS -> {
                    binding.textLoading.visibility = View.GONE
                    binding.progressBar.visibility = View.GONE
                    UiHandler.toastSuccessMessage(
                        requireContext(),
                        resource.data?.message.toString()
                    )
                    findNavController().popBackStack()
                }

                Status.ERROR -> {
                    binding.textLoading.visibility = View.GONE
                    binding.progressBar.visibility = View.GONE
                    UiHandler.toastErrorMessage(requireContext(), resource.message.toString())
                }
            }
        }
    }
}