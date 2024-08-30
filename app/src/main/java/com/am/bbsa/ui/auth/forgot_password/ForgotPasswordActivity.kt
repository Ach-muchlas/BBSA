package com.am.bbsa.ui.auth.forgot_password

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.am.bbsa.databinding.ActivityForgotPasswordBinding
import com.am.bbsa.service.source.Status
import com.am.bbsa.ui.auth.AuthViewModel
import com.am.bbsa.ui.auth.login.LoginActivity
import com.am.bbsa.ui.auth.otp.OtpForgotPasswordActivity
import com.am.bbsa.utils.UiHandler
import com.am.bbsa.utils.finish
import com.am.bbsa.utils.goToActivity
import org.koin.android.ext.android.inject

class ForgotPasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityForgotPasswordBinding
    private val viewModel: AuthViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setupNavigation()
        setupView()
        setContentView(binding.root)
    }

    private fun setupView(){
        UiHandler.setHintBehavior(binding.edlPhoneNumber)
    }

    private fun setupPostDataToApi() {
        val phoneNumber = binding.edtPhoneNumber.text
        viewModel.forgotPassword(phoneNumber.toString()).observe(this) { resource ->
            when (resource.status) {
                Status.LOADING -> {}
                Status.SUCCESS -> {
                    val data = resource.data?.data
                    UiHandler.toastSuccessMessage(this, resource.data?.message.toString())
                    goToActivity(OtpForgotPasswordActivity())
                    viewModel.saveCredentialRegister(data!!)
                }

                Status.ERROR -> {
                    UiHandler.toastErrorMessage(this, resource.message.toString())
                }
            }
        }
    }

    private fun setupNavigation() {
        binding.imageViewButtonBack.setOnClickListener {
            goToActivity(LoginActivity())
        }
        binding.buttonSave.setOnClickListener {
            setupPostDataToApi()
        }
    }
}