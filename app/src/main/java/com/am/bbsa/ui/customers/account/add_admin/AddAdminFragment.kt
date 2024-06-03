package com.am.bbsa.ui.customers.account.add_admin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.am.bbsa.R
import com.am.bbsa.databinding.FragmentAddAdminBinding
import com.am.bbsa.service.source.Status
import com.am.bbsa.ui.customers.account.AccountViewModel
import com.am.bbsa.utils.UiHandler
import org.koin.android.ext.android.inject

class AddAdminFragment : Fragment() {

    private var _binding: FragmentAddAdminBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AccountViewModel by inject()
    private var selectedGender: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddAdminBinding.inflate(inflater, container, false)
        setupDropdown()
        setupNavigation()
        initialize()

        return binding.root
    }

    private fun initialize() {
        UiHandler.setupVisibilityBottomNavigationAdmin(activity, true)
    }

    private fun setupNavigation() {
        binding.viewAppBar.buttonBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.buttonAdd.setOnClickListener {
            addAdmin()
        }
    }

    private fun setupDropdown() {
        val genderOption = listOf(getString(R.string.men), getString(R.string.women))
        /*view dropdown*/
        val autoComplete = binding.autoCompleteGender
        val genderAdapter = ArrayAdapter(requireActivity(), R.layout.dropdown_item, genderOption)
        autoComplete.setAdapter(genderAdapter)
        autoComplete.onItemClickListener = AdapterView.OnItemClickListener { adapterView, _, i, _ ->
            selectedGender = adapterView.getItemAtPosition(i) as? String
        }
    }

    private fun addAdmin() {
        /*menampung data edit text di variabel*/
        val name = binding.edtFullName.text.toString()
        val noTelephone = binding.edtNumberPhone.text.toString()
        val password = binding.edtPassword.text.toString()
        val address = binding.edtAddress.text.toString()

        if (!validateField(name, "Name")) {
            return
        } else if (!validateField(noTelephone, "Nomor Telephone")) {
            return
        } else if (!validateField(password, "Password")) {
            return
        } else if (!validateField(address, "Alamat")) {
            return
        }

        viewModel.createAdmin(
            name,
            noTelephone,
            selectedGender.toString(),
            address,
            password
        )
            .observe(viewLifecycleOwner)
            { resource ->
                when (resource.status) {
                    Status.LOADING -> {
                        binding.textLoading.visibility = View.VISIBLE
                        UiHandler.setupVisibilityProgressBar(binding.progressBar, true)

                    }

                    Status.SUCCESS -> {
                        binding.textLoading.visibility = View.GONE
                        UiHandler.setupVisibilityProgressBar(binding.progressBar, false)
                        UiHandler.toastSuccessMessage(
                            requireContext(),
                            resource.data?.message.toString()
                        )
                        findNavController().popBackStack()
                    }

                    Status.ERROR -> {
                        binding.textLoading.visibility = View.GONE
                        UiHandler.setupVisibilityProgressBar(binding.progressBar, false)
                        UiHandler.toastErrorMessage(requireContext(), resource.message.toString())
                    }
                }
            }
    }

    private fun validateField(field: String, fieldName: String): Boolean {
        if (field.isEmpty()) {
            UiHandler.toastErrorMessage(requireContext(), "$fieldName tidak boleh kosong.")
            return false
        }
        return true
    }


    override fun onDestroy() {
        UiHandler.setupVisibilityBottomNavigationAdmin(activity, false)
        super.onDestroy()
    }
}