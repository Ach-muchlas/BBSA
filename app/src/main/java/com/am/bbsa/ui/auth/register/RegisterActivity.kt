package com.am.bbsa.ui.auth.register

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.am.bbsa.R
import com.am.bbsa.databinding.ActivityRegisterBinding
import com.am.bbsa.ui.auth.login.LoginActivity
import com.am.bbsa.ui.auth.otp.OtpActivity
import com.am.bbsa.utils.goToActivity

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun setupNavigation() {
        /*back to login*/
        binding.buttonBack.setOnClickListener {
            goToActivity(LoginActivity())
        }
        binding.textLogin.setOnClickListener {
            goToActivity(LoginActivity())
        }

        /*Intent to OTP*/
        binding.buttonRegister.setOnClickListener {
            goToActivity(OtpActivity())
        }
    }
}