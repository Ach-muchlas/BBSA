package com.am.bbsa.ui.admin.menu.waste_deposit

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.am.bbsa.R
import com.am.bbsa.adapter.menu.SelectNasabahWasteDepositAdapter
import com.am.bbsa.data.response.DataItemNasabah
import com.am.bbsa.databinding.FragmentWasteDepositAdminBinding
import com.am.bbsa.service.source.Status
import com.am.bbsa.ui.admin.menu.MenuViewModel
import com.am.bbsa.ui.auth.AuthViewModel
import com.am.bbsa.ui.bottom_sheet.ChooseGalleryOrCamera2BottomSheet
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

class WasteDepositAdminFragment : Fragment() {
    private var _binding: FragmentWasteDepositAdminBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: SelectNasabahWasteDepositAdapter

    /*instantiation of firebase storage object to send images*/
    private lateinit var storage: FirebaseStorage
    private lateinit var storageReference: StorageReference
    private lateinit var firebaseFirestore: FirebaseFirestore

    /*instantiation of the object to store the image uri*/
    private lateinit var currentImageUri: Uri
    private var selectedIdNasabah: Int? = null

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
        _binding = FragmentWasteDepositAdminBinding.inflate(inflater, container, false)
        setupDataNasabah()
        setupNavigation()
        initVars()
        setupView()
        return binding.root
    }

    private fun initVars() {
        /*inisialisasi object firebase*/
        storage = Firebase.storage
        storageReference = storage.reference
        firebaseFirestore = FirebaseFirestore.getInstance()
    }

    private fun setupView() {
        UiHandler.setupVisibilityBottomNavigationAdmin(activity, true)
        binding.viewAppBar.textTitleAppBar.text = getString(R.string.waste_deposit)
        binding.edtDate.setText(Formatter.formatDateTime(LocalDateTime.now()))
    }

    private fun setupNavigation() {
        /*return to previous page*/
        binding.viewAppBar.buttonBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.cardValuePhoto.setOnClickListener {
            ChooseGalleryOrCamera2BottomSheet.show(childFragmentManager) { uri ->
                currentImageUri = uri
                binding.imageWaste.setImageURI(uri)
            }
        }

        binding.buttonDeposit.setOnClickListener {
            if (currentImageUri != null) {
                uploadImageToFirebase(currentImageUri)
            } else {
                setupPostDataToApi("photo")
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
        val userId: Int = selectedIdNasabah ?: 0
        viewModel.createDepositWasteAdmin(token, userId, imageUrl)
            .observe(viewLifecycleOwner) { resource ->
                when (resource.status) {
                    Status.LOADING -> {
                        binding.progressBar.visibility = View.VISIBLE
                        binding.textLoading.visibility = View.VISIBLE
                    }

                    Status.SUCCESS -> {
                        binding.progressBar.visibility = View.GONE
                        binding.textLoading.visibility = View.GONE
                        UiHandler.toastSuccessMessage(requireContext(), "Berhasil Setor sampah")
                        findNavController().popBackStack()
                    }

                    Status.ERROR -> {
                        binding.progressBar.visibility = View.GONE
                        binding.textLoading.visibility = View.GONE
                        UiHandler.toastErrorMessage(
                            requireContext(),
                            resource.message.toString()
                        )
                    }
                }
            }
    }

    private fun setupDropDown(nasabah: List<DataItemNasabah>) {
        adapter =
            SelectNasabahWasteDepositAdapter(requireContext(), R.layout.dropdown_item, nasabah)
        /*view dropdown*/
        val autoComplete = binding.autoCompleteName
        autoComplete.setAdapter(adapter)
        autoComplete.onItemClickListener = AdapterView.OnItemClickListener { _, _, i, _ ->
            val selectedNasabah = adapter.getItem(i)
            selectedIdNasabah = selectedNasabah.userId

            binding.autoCompleteName.setText(buildString {
                append(selectedNasabah.userId)
                append(". ")
                append(selectedNasabah.name)
            })
            setupDataNasabah()
        }
    }

    private fun setupDataNasabah() {
        viewModel.showAllNasabah(token).observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                Status.LOADING -> {

                }

                Status.SUCCESS -> {
                    val data = resource.data?.data as List<DataItemNasabah>
                    setupDropDown(data)
                    adapter.notifyDataSetChanged()
                }

                Status.ERROR -> {
                    UiHandler.toastErrorMessage(requireContext(), resource.message.toString())
                }
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        UiHandler.setupVisibilityBottomNavigationAdmin(activity, false)
    }
}