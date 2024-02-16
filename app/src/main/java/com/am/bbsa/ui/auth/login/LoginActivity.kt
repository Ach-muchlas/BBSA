package com.am.bbsa.ui.auth.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.am.bbsa.databinding.ActivityLoginBinding
import com.am.bbsa.ui.auth.register.RegisterActivity
import com.am.bbsa.utils.goToActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
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
    }
}