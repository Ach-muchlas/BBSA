package com.am.bbsa.ui.customers.home.waste_deposit

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.am.bbsa.R
import com.am.bbsa.adapter.menu.SelectNasabahWasteDepositAdapter
import com.am.bbsa.data.response.UserResponse
import com.am.bbsa.databinding.FragmentWasteDepositBinding
import com.am.bbsa.service.source.Status
import com.am.bbsa.ui.admin.menu.MenuViewModel
import com.am.bbsa.ui.auth.AuthViewModel
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
    private val homeViewModel: HomeViewModel by inject()
    private lateinit var adapter: SelectNasabahWasteDepositAdapter

    /*instantiation of firebase storage object to send images*/
    private lateinit var storage: FirebaseStorage
    private lateinit var storageReference: StorageReference
    private lateinit var firebaseFirestore: FirebaseFirestore

    /*instantiation of the object to store the image uri*/
    private var imageUri: Uri? = null
    private var selectedIdNasabah: Int? = null
    private lateinit var imageUrl: String

    /*initialize view model*/
    private val viewModel: MenuViewModel by inject()
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

    /*intent to object foto*/
    private val resultLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) {
        imageUri = it
        binding.imageWaste.setImageURI(it)
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
            uploadImageToFirebaseAndPostApiForDatabase()
        }

        binding.cardValuePhoto.setOnClickListener {
            resultLauncher.launch("image/*")
        }
    }

    /*fungsi ini berjalan akan mengirim image ke firebase
    setelah itu akan akan mendowload url dan dimasukkan kedalam viewmodel*/
    private fun uploadImageToFirebaseAndPostApiForDatabase() {
        imageUri?.let { filePath ->
            val ref = storageReference.child("Images/SetoranSampah/${UUID.randomUUID()}")

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
        val date = LocalDateTime.now().toString()
        val userId = selectedIdNasabah ?: 0
        viewModel.createDepositWaste(token, userId, date, imageUrl)
            .observe(viewLifecycleOwner) { resource ->
                when (resource.status) {
                    Status.LOADING -> {}
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
        homeViewModel.showDataUser(token).observe(viewLifecycleOwner) { resource ->
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