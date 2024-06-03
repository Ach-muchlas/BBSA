package com.am.bbsa.ui.admin.menu.news

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.am.bbsa.R
import com.am.bbsa.data.response.DataItemNews
import com.am.bbsa.databinding.FragmentAddOrUpdateNewsBinding
import com.am.bbsa.service.source.Status
import com.am.bbsa.ui.admin.menu.MenuViewModel
import com.am.bbsa.ui.auth.AuthViewModel
import com.am.bbsa.ui.bottom_sheet.ChooseGalleryOrCamera2BottomSheet
import com.am.bbsa.utils.KEY
import com.am.bbsa.utils.UiHandler
import com.bumptech.glide.Glide
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.storage
import org.koin.android.ext.android.inject
import java.util.UUID

class UpdateNewsFragment : Fragment() {
    private var _binding: FragmentAddOrUpdateNewsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MenuViewModel by inject()
    private val authViewModel: AuthViewModel by inject()
    private lateinit var storage: FirebaseStorage
    private lateinit var storageReference: StorageReference
    private lateinit var firebaseFirestore: FirebaseFirestore
    private val token by lazy {
        authViewModel.getCredentialUser()?.token.toString()
    }
    private lateinit var currentImageUri: Uri
    private val receiveBundle by lazy {
        arguments?.getParcelable<DataItemNews>(KEY.BUNDLE_PARCELABLE_NEWS)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddOrUpdateNewsBinding.inflate(inflater, container, false)
        setupView()
        setupNavigation()
        initVars()
        return binding.root
    }

    private fun setupView() {
        binding.viewAppbar.textTitleAppBar.text = getString(R.string.update_news)
        Glide.with(requireContext()).load(receiveBundle?.photo).into(binding.imageNews)
        binding.edtTitle.setText(receiveBundle?.title ?: "")
        binding.edtDescription.setText(receiveBundle?.description)
    }

    private fun setupNavigation() {
        binding.viewAppbar.buttonBack.setOnClickListener { findNavController().popBackStack() }
        binding.buttonSave.setOnClickListener {
            if (currentImageUri != null) {
                uploadImageToFirebase(currentImageUri)
            } else {
                setupPostDataToApi(receiveBundle?.photo.toString())
            }
        }
        binding.cardPhoto.setOnClickListener {
            ChooseGalleryOrCamera2BottomSheet.show(childFragmentManager) { uri ->
                currentImageUri = uri
                binding.imageNews.setImageURI(uri)
            }
        }
    }

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

    private fun initVars() {
        storage = Firebase.storage
        storageReference = storage.reference
        firebaseFirestore = FirebaseFirestore.getInstance()
    }

    private fun setupPostDataToApi(imageUrl: String) {
        val title = binding.edtTitle.text
        val desc = binding.edtDescription.text
        val id = receiveBundle?.id ?: 0

        viewModel.updateNews(token, id, title.toString(), desc.toString(), imageUrl)
            .observe(viewLifecycleOwner) { resource ->
                when (resource.status) {
                    Status.LOADING -> {
                        binding.textLoading.visibility = View.VISIBLE
                        binding.progressBar.visibility = View.VISIBLE
                    }

                    Status.SUCCESS -> {
                        binding.textLoading.visibility = View.GONE
                        binding.progressBar.visibility = View.GONE
                        UiHandler.toastSuccessMessage(
                            requireContext(), resource.data?.message.toString()
                        )
                        findNavController().popBackStack()
                    }

                    Status.ERROR -> {
                        binding.textLoading.visibility = View.GONE
                        binding.progressBar.visibility = View.GONE
                        UiHandler.toastErrorMessage(
                            requireContext(), resource.message.toString()
                        )
                    }
                }
            }
    }

}