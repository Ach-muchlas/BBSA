package com.am.bbsa.ui.auth.otp

import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import com.am.bbsa.R
import com.am.bbsa.databinding.ActivityOtpBinding
import com.am.bbsa.service.source.Status
import com.am.bbsa.ui.auth.AuthViewModel
import com.am.bbsa.ui.auth.forgot_password.ResetPasswordActivity
import com.am.bbsa.utils.UiHandler
import com.am.bbsa.utils.goToActivity
import org.koin.android.ext.android.inject

class OtpForgotPasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOtpBinding
    private val viewModel: AuthViewModel by inject()
    private lateinit var countDownTimer: CountDownTimer

    private val interval: Long = 1000
    private val totalDuration: Long = 120000

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupCountDown()
        setupNavigation()
        setupView()
    }

    private fun setupNavigation() {
        setupEditText()
        binding.textResendingOtp.setOnClickListener {
            setupResendingOtp()
        }
    }

    private fun setupResendingOtp() {
        viewModel.resendingOtpForgotPassword(phoneNumber).observe(this) { resource ->
            when (resource.status) {
                Status.LOADING -> {}
                Status.SUCCESS -> {
                    UiHandler.toastSuccessMessage(this, resource.data?.message.toString())
                }

                Status.ERROR -> {
                    UiHandler.toastErrorMessage(this, resource.message.toString())
                }
            }
        }
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
                binding.textTimeExpiredOtp.text = getString(R.string.time_out)
            }
        }.start()
    }

    private fun setupView() {
        binding.textViewDestinationOtp.text = phoneNumber
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
                            setupPostDataToApi()
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
        val userId = viewModel.getCredentialRegister().id
        viewModel.verificationOtpForgotPassword(userId ?: 0, setupMergeOTP())
            .observe(this) { resource ->
                when (resource.status) {
                    Status.LOADING -> {}
                    Status.SUCCESS -> {
                        UiHandler.toastSuccessMessage(
                            this,
                            resource.data?.message.toString()
                        )
                        goToActivity(ResetPasswordActivity())
                    }

                    Status.ERROR -> {
                        UiHandler.toastErrorMessage(
                            this,
                            resource.message.toString()
                        )
                    }
                }
            }
    }

    private fun setupMergeOTP(): String {
        val otp = StringBuilder()
        for (i in edtTextList) {
            otp.append(i.text)
        }
        return otp.toString()
    }

    private fun clearValueAllEditText() {
        for (i in edtTextList) {
            i.text.clear()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        countDownTimer.cancel()
    }
}