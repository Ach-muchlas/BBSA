package com.am.bbsa.ui.customers.home.waste_deposit

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.am.bbsa.R
import com.am.bbsa.data.response.UserResponse
import com.am.bbsa.databinding.FragmentWasteDepositBinding
import com.am.bbsa.service.source.Status
import com.am.bbsa.ui.auth.AuthViewModel
import com.am.bbsa.ui.bottom_sheet.ChooseGalleryOrCamera2BottomSheet
import com.am.bbsa.ui.customers.home.HomeViewModel
import com.am.bbsa.utils.Formatter
import com.am.bbsa.utils.UiHandler
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.storage
import org.koin.android.ext.android.inject
import java.time.LocalDateTime
import java.util.UUID

class WasteDepositFragment : Fragment() {
    private var _binding: FragmentWasteDepositBinding? = null
    private val binding get() = _binding!!

    /*instantiation of firebase storage object to send images*/
    private lateinit var storage: FirebaseStorage
    private lateinit var storageReference: StorageReference
    private lateinit var firebaseFirestore: FirebaseFirestore

    /*instantiation of the object to store the image uri*/
    private var selectedIdNasabah: Int? = null
    private lateinit var currentImageUri: Uri

    /*initialize view model*/
    private val viewModel: HomeViewModel by inject()
    private val authViewModel: AuthViewModel by inject()

    private val token by lazy {
        authViewModel.getCredentialUser()?.token.toString()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWasteDepositBinding.inflate(inflater, container, false)
        setupNavigation()
        initVars()
        setupDataUser()
        return binding.root
    }


    private fun initVars() {
        /*inisialisasi object firebase*/
        storage = Firebase.storage
        storageReference = storage.reference
        firebaseFirestore = FirebaseFirestore.getInstance()
    }

    private fun setupView(data: UserResponse?) {
        UiHandler.setupVisibilityBottomNavigationNasabah(activity, true)
        binding.viewAppBar.textTitleAppBar.text = getString(R.string.waste_deposit)
        binding.edtName.setText(data?.data?.name)
        binding.edtDate.setText(Formatter.formatDateTime(LocalDateTime.now()))
    }

    private fun setupNavigation() {
        binding.viewAppBar.buttonBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.buttonDeposit.setOnClickListener {
            if (currentImageUri != null) {
                uploadImageToFirebase(currentImageUri)
            } else {
                setupPostDataToApi("photo")
            }
        }

        binding.cardValuePhoto.setOnClickListener {
            ChooseGalleryOrCamera2BottomSheet.show(childFragmentManager) { uri ->
                currentImageUri = uri
                binding.imageWaste.setImageURI(uri)
            }
        }
    }

    /*fungsi ini berjalan akan mengirim image ke firebase
    setelah itu akan akan mendowload url dan dimasukkan kedalam viewmodel*/
    private fun uploadImageToFirebase(imageUri: Uri) {
        val ref = storageReference.child("Images/Setoran Sampah/${UUID.randomUUID()}")
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
        viewModel.createDepositWaste(token, imageUrl)
            .observe(viewLifecycleOwner) { resource ->
                when (resource.status) {
                    Status.LOADING -> {
                        binding.textLoading.visibility = View.VISIBLE
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    Status.SUCCESS -> {
                        UiHandler.toastSuccessMessage(requireContext(), "Berhasil Setor sampah")
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

    private fun setupDataUser() {
        viewModel.showDataUser(token).observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                Status.LOADING -> {}
                Status.SUCCESS -> {
                    selectedIdNasabah = resource.data?.data?.nasabah?.userId
                    setupView(resource.data)
                }

                Status.ERROR -> {}
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        UiHandler.setupVisibilityBottomNavigationNasabah(activity, false)
    }
}