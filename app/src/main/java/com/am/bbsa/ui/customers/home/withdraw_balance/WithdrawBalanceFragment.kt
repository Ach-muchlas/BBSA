package com.am.bbsa.ui.customers.home.withdraw_balance

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import com.am.bbsa.R
import com.am.bbsa.adapter.home.SelectWithdrawBalanceMethodAdapter
import com.am.bbsa.data.body.TarikSaldoBody
import com.am.bbsa.data.dummy_data.DummyData
import com.am.bbsa.databinding.FragmentWithdrawBalanceBinding
import com.am.bbsa.service.source.Status
import com.am.bbsa.ui.auth.AuthViewModel
import com.am.bbsa.ui.bottom_sheet.SuccessWithdrawBalanceBottomSheetFragment
import com.am.bbsa.ui.customers.home.HomeViewModel
import com.am.bbsa.utils.Destination
import com.am.bbsa.utils.Formatter
import com.am.bbsa.utils.KEY
import com.am.bbsa.utils.Navigation
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
        setFragmentResultListener("withdraw_result") { key, bundle ->
            val externalId = bundle.getString("externalId")
            val bottomSheet = SuccessWithdrawBalanceBottomSheetFragment(externalId.toString())
            bottomSheet.show(childFragmentManager, bottomSheet.tag)
        }
        setupView()
        setupNavigation()
        setupDropdown()
        return binding.root
    }

    private fun setupView() {
        UiHandler.setupVisibilityBottomNavigationNasabah(activity, true)
        UiHandler.setHintBehavior(
            binding.edlName, binding.edlAmount, binding.edlMethodWithdraw, binding.edlAccountNumber
        )
        binding.viewAppBar.textTitleAppBar.text = getString(R.string.withdraw_balance)

        binding.edtAmount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                val amountString = p0.toString()
                if (amountString.isNotEmpty()) {
                    val total = amountString.toInt() + 2500
                    binding.textTotal.text = Formatter.formatCurrency(total)
                } else {
                    binding.textTotal.text = Formatter.formatCurrency(0)
                }
            }
        })
    }

    private fun setupNavigation() {
        binding.viewAppBar.buttonBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.buttonWithdraw.setOnClickListener {
            if (validateInput()) {
                setupPostDataToApi()
            }
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

        val dataBundle = TarikSaldoBody(
            bankCode,
            accountName.toString(),
            accountNumber.toString(),
            amount.toString().toInt(),
            0
        )
        val bundle = Bundle().apply {
            putParcelable(KEY.KEY_WITHDRAW_TO_OTP, dataBundle)
        }

        viewModel.sendOTPWithdrawBalance(token).observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                Status.LOADING -> {}
                Status.SUCCESS -> {
                    clearText()
                    Navigation.navigationFragment(
                        Destination.WITHDRAW_TO_OTP_WITHDRAW,
                        findNavController(),
                        bundle
                    )
                }

                Status.ERROR -> {
                    clearText()
                    UiHandler.toastErrorMessage(requireContext(), resource.message.toString())
                }
            }
        }
    }

    private fun validateInput(): Boolean {
        val bankCode = selectedBankCode
        val accountName = binding.edtName.text.toString()
        val accountNumber = binding.edtAccountNumber.text.toString()
        val amount = binding.edtAmount.text.toString()

        return when {
            accountName.isEmpty() -> {
                UiHandler.toastErrorMessage(requireContext(), "Mohon masukan nama rekening anda!!.")
                false
            }

            bankCode.isNullOrEmpty() -> {
                UiHandler.toastErrorMessage(requireContext(), "Mohon memilih bank tujuan anda!!.")
                false
            }

            accountNumber.isEmpty() -> {
                UiHandler.toastErrorMessage(
                    requireContext(),
                    "Mohon masukan nomor rekening tujuan anda!!."
                )
                false
            }

            amount.isEmpty() -> {
                UiHandler.toastErrorMessage(
                    requireContext(),
                    "Mohon masukan jumlah penarikan anda!!."
                )
                false
            }

            amount.toInt() < 10000 -> {
                UiHandler.toastErrorMessage(
                    requireContext(),
                    "Maaf jumlah penarikan harus lebih dari Rp. 10.000"
                )
                false
            }

            else -> true
        }
    }

    private fun clearText() {
        binding.edtName.setText("")
        binding.edtAmount.setText("")
        binding.edtAccountNumber.setText("")
        binding.autoCompleteMethodWithdraw.setText("", false)
        selectedBankCode = null
    }


    override fun onDestroy() {
        super.onDestroy()
        UiHandler.setupVisibilityBottomNavigationNasabah(activity, false)
    }

}