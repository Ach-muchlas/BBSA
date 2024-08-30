package com.am.bbsa.ui.auth.forgot_password

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.am.bbsa.databinding.ActivityResetPasswordBinding
import com.am.bbsa.service.source.Status
import com.am.bbsa.ui.auth.AuthViewModel
import com.am.bbsa.ui.auth.login.LoginActivity
import com.am.bbsa.utils.UiHandler
import com.am.bbsa.utils.finish
import org.koin.android.ext.android.inject

class ResetPasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResetPasswordBinding
    private val viewModel: AuthViewModel by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResetPasswordBinding.inflate(layoutInflater)
        setupNavigation()
        setupView()
        setContentView(binding.root)
    }

    private fun setupView() {
        UiHandler.setHintBehavior(binding.edlPassword)
    }

    private fun setupNavigation() {
        binding.buttonSave.setOnClickListener {
            setupPostDataToApi()
        }
    }

    private fun setupPostDataToApi() {
        val userId = viewModel.getCredentialRegister().id
        val newPassword = binding.edtPassword.text

        if (!UiHandler.validatePassword(newPassword.toString(), this)){
            return
        }
        viewModel.resetPassword(userId ?: 0, newPassword.toString()).observe(this) { resource ->
            when (resource.status) {
                Status.LOADING -> {}
                Status.SUCCESS -> {
                    UiHandler.toastSuccessMessage(this, resource.data?.message.toString())
                    Intent(this, LoginActivity::class.java).finish(this)
                }

                Status.ERROR -> {
                    UiHandler.toastErrorMessage(this, resource.message.toString())
                }
            }
        }
    }
}