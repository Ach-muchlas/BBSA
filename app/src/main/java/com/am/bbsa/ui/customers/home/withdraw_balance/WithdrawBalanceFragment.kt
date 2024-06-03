package com.am.bbsa.ui.customers.home.withdraw_balance

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.am.bbsa.R
import com.am.bbsa.adapter.home.SelectWithdrawBalanceMethodAdapter
import com.am.bbsa.data.dummy_data.DummyData
import com.am.bbsa.databinding.FragmentWithdrawBalanceBinding
import com.am.bbsa.service.source.Status
import com.am.bbsa.ui.auth.AuthViewModel
import com.am.bbsa.ui.bottom_sheet.SuccessWithdrawBalanceBottomSheetFragment
import com.am.bbsa.ui.customers.home.HomeViewModel
import com.am.bbsa.utils.Formatter
import com.am.bbsa.utils.UiHandler
import org.koin.android.ext.android.inject

class WithdrawBalanceFragment : Fragment() {
    private var _binding: FragmentWithdrawBalanceBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: SelectWithdrawBalanceMethodAdapter

    private val authViewModel: AuthViewModel by inject()
    private val viewModel: HomeViewModel by inject()
    private val token: String by lazy { authViewModel.getCredentialUser()?.token.toString() }

    private var selectedBankCode: String? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWithdrawBalanceBinding.inflate(inflater, container, false)
        setupView()
        setupNavigation()
        setupDropdown()
        return binding.root
    }

    private fun setupView() {
        UiHandler.setupVisibilityBottomNavigationNasabah(activity, true)
        binding.viewAppBar.textTitleAppBar.text = getString(R.string.withdraw_balance)

        binding.edtAmount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                val total = p0.toString().toInt() + 2500
                binding.textTotal.text = Formatter.formatCurrency(total)
            }
        })
    }

    private fun setupNavigation() {
        binding.viewAppBar.buttonBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.buttonWithdraw.setOnClickListener {
            setupPostDataToApi()
        }
    }

    private fun setupDropdown() {
        adapter = SelectWithdrawBalanceMethodAdapter(
            requireContext(),
            R.layout.dropdown_item,
            DummyData.DataListBank
        )
        val dropdown = binding.autoCompleteMethodWithdraw
        dropdown.setOnClickListener { dropdown.showDropDown() }
        dropdown.setAdapter(adapter)
        dropdown.onItemClickListener = AdapterView.OnItemClickListener { _, _, i, _ ->
            val selectedBank = adapter.getItem(i)
            selectedBankCode = selectedBank.bankCode
            binding.autoCompleteMethodWithdraw.setText(selectedBank.nameBank, false)
        }
    }

    private fun setupPostDataToApi() {
        val bankCode: String = selectedBankCode.toString()
        val accountName = binding.edtName.text
        val accountNumber = binding.edtAccountNumber.text
        val amount = binding.edtAmount.text
        viewModel.createWithdrawBalance(
            token,
            bankCode,
            accountName.toString(),
            accountNumber.toString(),
            amount.toString().toInt()
        ).observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                Status.LOADING -> {}
                Status.SUCCESS -> {
                    clearText()
                    val dataExternalId = resource.data?.data?.externalId.toString()
                    val bottomSheet = SuccessWithdrawBalanceBottomSheetFragment(dataExternalId)
                    bottomSheet.show(childFragmentManager, bottomSheet.tag)
                }

                Status.ERROR -> {
                    UiHandler.toastErrorMessage(requireContext(), resource.message.toString())
                    clearText()
                }
            }
        }
    }

    private fun clearText() {
        binding.edtName.setText("")
        binding.edtAmount.setText("")
        binding.edtAccountNumber.setText("")
    }


    override fun onDestroy() {
        super.onDestroy()
        UiHandler.setupVisibilityBottomNavigationNasabah(activity, false)
    }

}