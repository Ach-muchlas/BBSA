package com.am.bbsa.ui.auth.otp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.am.bbsa.ui.customers.main.CustomersMainActivity
import com.am.bbsa.databinding.ActivityOtpBinding
import com.am.bbsa.utils.goToActivity

class OtpActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOtpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupNavigation()
    }

    private fun setupNavigation() {
        binding.textViewRequestNewCode.setOnClickListener {
            goToActivity(CustomersMainActivity())
        }
    }
}