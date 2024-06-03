package com.am.bbsa.ui.admin.menu.waste_type_information

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.am.bbsa.R
import com.am.bbsa.data.response.DataItemSampah
import com.am.bbsa.databinding.FragmentAddOrUpdateWasteTypeBinding
import com.am.bbsa.service.source.Status
import com.am.bbsa.ui.admin.menu.MenuViewModel
import com.am.bbsa.ui.auth.AuthViewModel
import com.am.bbsa.ui.bottom_sheet.ChooseGalleryOrCamera2BottomSheet
import com.am.bbsa.utils.Formatter
import com.am.bbsa.utils.UiHandler
import com.bumptech.glide.Glide
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.storage
import org.koin.android.ext.android.inject
import java.util.UUID

class UpdateWasteTypeFragment : Fragment() {
    private var _binding: FragmentAddOrUpdateWasteTypeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MenuViewModel by inject()
    private val authViewModel: AuthViewModel by inject()
    private lateinit var storage: FirebaseStorage
    private lateinit var storageReference: StorageReference
    private lateinit var firebaseFirestore: FirebaseFirestore
    private val receive: DataItemSampah? by lazy {
        arguments?.getParcelable(WasteTypeInformationFragment.KEY_DATA_SAMPAH)
    }
    private val token by lazy {
        authViewModel.getCredentialUser()?.token.toString()
    }
    private lateinit var currentImageUri: Uri

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddOrUpdateWasteTypeBinding.inflate(inflater, container, false)
        setupNavigation()
        initVars()
        setupView()
        return binding.root
    }

    private fun setupNavigation() {
        binding.buttonSave.setOnClickListener {
            if (currentImageUri != null) {
                uploadImageToFirebase(currentImageUri)
            } else {
                setupPostDataToApi(receive?.photo.toString())
            }
        }
        binding.cardPhoto.setOnClickListener {
            ChooseGalleryOrCamera2BottomSheet.show(childFragmentManager) { uri ->
                currentImageUri = uri
                binding.imageWaste.setImageURI(uri)
            }
        }
        binding.viewAppbar.buttonBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setupView() {
        binding.viewAppbar.textTitleAppBar.text = getString(R.string.update_data)
        binding.edtTypeWaste.setText(receive?.type)
        binding.edtNameWaste.setText(receive?.name)
        binding.edtPrice.setText(Formatter.formatCurrency(receive?.price ?: 0))

        Glide.with(requireContext()).load(receive?.photo).into(binding.imageWaste)
    }


    private fun initVars() {
        storage = Firebase.storage
        storageReference = storage.reference
        firebaseFirestore = FirebaseFirestore.getInstance()
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

    private fun setupPostDataToApi(imageUrl: String) {
        val id = receive?.id ?: 0
        val name = binding.edtNameWaste.text.toString()
        val type = binding.edtTypeWaste.text.toString()
        val price = binding.edtPrice.text
        val priceWaste = price.toString().replace(Regex("\\D"), "").toIntOrNull()

        viewModel.updateInformationWaste(token, id, name, type, priceWaste ?: 0, imageUrl)
            .observe(viewLifecycleOwner) { resource ->
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
                        UiHandler.toastErrorMessage(
                            requireContext(),
                            resource.message.toString()
                        )
                    }
                }
            }
    }

}