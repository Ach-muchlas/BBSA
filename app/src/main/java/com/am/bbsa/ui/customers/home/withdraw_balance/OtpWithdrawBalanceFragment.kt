package com.am.bbsa.ui.customers.home.withdraw_balance

import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import com.am.bbsa.R
import com.am.bbsa.data.body.TarikSaldoBody
import com.am.bbsa.databinding.FragmentOtpWithdrawBalanceBinding
import com.am.bbsa.service.source.Status
import com.am.bbsa.ui.auth.AuthViewModel
import com.am.bbsa.ui.bottom_sheet.SuccessWithdrawBalanceBottomSheetFragment
import com.am.bbsa.ui.customers.home.HomeViewModel
import com.am.bbsa.utils.KEY
import com.am.bbsa.utils.UiHandler
import org.koin.android.ext.android.inject

class OtpWithdrawBalanceFragment : Fragment() {
    private var _binding: FragmentOtpWithdrawBalanceBinding? = null
    private val binding get() = _binding!!
    private lateinit var countDownTimer: CountDownTimer
    private val viewModel: AuthViewModel by inject()
    private val homeViewModel: HomeViewModel by inject()
    private val interval: Long = 1000
    private val totalDuration: Long = 120000

    private var isTimeExpired: Boolean = false

    private val token: String by lazy { viewModel.getCredentialUser()?.token.toString() }
    private val phoneNumber: String by lazy {
        viewModel.getCredentialRegister().phoneNumber.toString()
    }
    private val edtTextList by lazy {
        listOf(
            binding.edtOtp1,
            binding.edtOtp2,
            binding.edtOtp3,
            binding.edtOtp4,
            binding.edtOtp5,
            binding.edtOtp6,
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOtpWithdrawBalanceBinding.inflate(inflater, container, false)
        setupView()
        setupCountDown()
        return binding.root
    }

    private fun setupCountDown() {
        countDownTimer = object : CountDownTimer(totalDuration, interval) {
            override fun onTick(millisUntilFinished: Long) {
                val minutes = millisUntilFinished / 1000 / 60
                val seconds = millisUntilFinished / 1000 % 60

                binding.textTimeExpiredOtp.text =
                    String.format("%02d menit %02d detik", minutes, seconds)
            }

            override fun onFinish() {
                isTimeExpired = true
                binding.textTimeExpiredOtp.text = getString(R.string.time_out)
                UiHandler.toastErrorMessage(
                    requireContext(),
                    getString(R.string.time_up_request_new_code)
                )
            }
        }.start()
    }

    private fun setupView() {
        setupEditText()
    }

    private fun setupEditText() {
        for (i in edtTextList.indices) {
            edtTextList[i].addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                override fun onTextChanged(text: CharSequence, p1: Int, p2: Int, p3: Int) {
                    if (text.length == 1) {
                        if (i < edtTextList.size - 1) {
                            edtTextList[i + 1].requestFocus()
                        } else {
                            if (isTimeExpired) {
                                UiHandler.toastErrorMessage(
                                    requireContext(),
                                    "Waktu sudah habis. Silakan minta kode baru."
                                )
                            } else {
                                setupPostDataToApi()
                            }
                            clearValueAllEditText()
                            edtTextList[0].requestFocus()
                        }
                    }
                }

                override fun afterTextChanged(p0: Editable?) {}
            })
        }
    }

    private fun setupPostDataToApi() {
        val argument = arguments?.getParcelable<TarikSaldoBody>(KEY.KEY_WITHDRAW_TO_OTP)
        homeViewModel.createWithdrawBalance(
            token,
            argument?.bankCode.toString(),
            argument?.accountName.toString(),
            argument?.accountNumber.toString(),
            argument?.amount ?: 0,
            setupMergeOTP()
        )
            .observe(viewLifecycleOwner) { resource ->
                when (resource.status) {
                    Status.LOADING -> {}
                    Status.SUCCESS -> {
                        val dataExternalId = resource.data?.data?.externalId.toString()
                        setFragmentResult(
                            "withdraw_result",
                            bundleOf("externalId" to dataExternalId)
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

    private fun setupMergeOTP(): Int {
        val otp = StringBuilder()
        for (i in edtTextList) {
            otp.append(i.text)
        }
        return otp.toString().toInt()
    }

    private fun clearValueAllEditText() {
        for (i in edtTextList) {
            i.text.clear()
        }
    }
}