package com.am.bbsa.ui.auth.login

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.am.bbsa.databinding.ActivityLoginBinding
import com.am.bbsa.ui.admin.main.AdminMainActivity
import com.am.bbsa.ui.auth.register.RegisterActivity
import com.am.bbsa.ui.customers.main.CustomersMainActivity
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
        val noPhoneCustomers = "000"
        val passwordCustomers = "nasabah"

        val noAdmin = "111"
        val passwordAdmin = "admin"

        binding.textRegister.setOnClickListener {
            goToActivity(RegisterActivity())
        }

        binding.buttonEnter.setOnClickListener {
            val phone = binding.edtNumberPhone.text.toString()
            val password = binding.edtPassword.text.toString()

            if (phone == noPhoneCustomers || password == passwordCustomers) {
                goToActivity(CustomersMainActivity())
            } else if (phone == noAdmin || password == passwordAdmin) {
                goToActivity(AdminMainActivity())
            } else {
                Toast.makeText(this, "Incorrect Email & Password", Toast.LENGTH_SHORT).show()
            }
        }


    }
}