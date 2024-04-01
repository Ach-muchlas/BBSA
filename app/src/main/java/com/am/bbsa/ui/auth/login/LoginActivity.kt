package com.am.bbsa.ui.auth.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.am.bbsa.data.response.LoginResult
import com.am.bbsa.databinding.ActivityLoginBinding
import com.am.bbsa.service.source.Status
import com.am.bbsa.ui.admin.main.AdminMainActivity
import com.am.bbsa.ui.auth.AuthViewModel
import com.am.bbsa.ui.auth.register.RegisterActivity
import com.am.bbsa.ui.customers.main.CustomersMainActivity
import com.am.bbsa.utils.UiHandler
import com.am.bbsa.utils.finish
import com.am.bbsa.utils.goToActivity
import org.koin.android.ext.android.inject

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val viewModel: AuthViewModel by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setupNavigation()
        setContentView(binding.root)
    }

    private fun setupNavigation() {
        binding.textRegister.setOnClickListener {
            goToActivity(RegisterActivity())
        }

        binding.buttonEnter.setOnClickListener {
            login()
        }
    }

    private fun login() {
        val phoneEditText = binding.edtNumberPhone.text
        val password = binding.edtPassword.text

        viewModel.login(phoneEditText.toString(), password.toString())
            .observe(this@LoginActivity) { resource ->
                when (resource.status) {
                    Status.LOADING -> {
                        UiHandler.setupVisibilityProgressBar(binding.progressCircular, true)
                    }

                    Status.SUCCESS -> {
                        UiHandler.setupVisibilityProgressBar(binding.progressCircular, false)
                        val token = LoginResult(role = resource.data?.data?.role, token = resource.data?.data?.token)
                        viewModel.saveCredentialUser(token)
                        viewModel.setSessionTimeout()
                        UiHandler.toastSuccessMessage(
                            this@LoginActivity,
                            resource.data?.message.toString()
                        )
                        if (resource.data?.data?.role.equals("Nasabah")) {
                            Intent(this, CustomersMainActivity::class.java).finish(this)
                        } else if (resource.data?.data?.role.equals("Admin")) {
                            Intent(this, AdminMainActivity::class.java).finish(this)
                        }
                    }

                    Status.ERROR -> {
                        UiHandler.setupVisibilityProgressBar(binding.progressCircular, false)
                        UiHandler.toastErrorMessage(this@LoginActivity, resource.message.toString())
                    }
                }
            }
    }
}