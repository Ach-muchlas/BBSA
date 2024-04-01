package com.am.bbsa.ui.auth.register

import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.am.bbsa.R
import com.am.bbsa.databinding.ActivityRegisterBinding
import com.am.bbsa.service.source.Status
import com.am.bbsa.ui.auth.AuthViewModel
import com.am.bbsa.ui.auth.login.LoginActivity
import com.am.bbsa.utils.UiHandler
import com.am.bbsa.utils.goToActivity
import org.koin.android.ext.android.inject

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val viewModel: AuthViewModel by inject()
    private var selectedGender: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setupView()
        setupNavigation()
        setContentView(binding.root)
    }

    private fun setupView() {
        val genderOption = listOf(getString(R.string.men), getString(R.string.women))
        /*view dropdown*/
        val autoComplete = binding.autoCompleteGender
        val genderAdapter = ArrayAdapter(this, R.layout.dropdown_item, genderOption)
        autoComplete.setAdapter(genderAdapter)
        autoComplete.onItemClickListener = AdapterView.OnItemClickListener { adapterView, _, i, _ ->
            selectedGender = adapterView.getItemAtPosition(i) as? String
        }
    }

    private fun setupNavigation() {/*back to login*/
        binding.buttonBack.setOnClickListener {
            goToActivity(LoginActivity())
        }
        binding.textLogin.setOnClickListener {
            goToActivity(LoginActivity())
        }

        /*Intent to OTP*/
        binding.buttonRegister.setOnClickListener {
            register()
//            goToActivity(OtpActivity())
        }
    }

    private fun register() {
        /*menampung data edit text di variabel*/
        val name = binding.edtFullName.text.toString()
        val noNIK = binding.edtNIK.text.toString()
        val noTelephone = binding.edtNumberPhone.text.toString()
        val password = binding.edtPassword.text.toString()
        val address = binding.edtAddress.text.toString()

        viewModel.register(name, noNIK, selectedGender.toString(), address, noTelephone, password)
            .observe(this@RegisterActivity)
            { resource ->
                when (resource.status) {
                    Status.LOADING -> {
                        UiHandler.setupVisibilityProgressBar(binding.progressCircular, true)
                    }

                    Status.SUCCESS -> {
                        UiHandler.setupVisibilityProgressBar(binding.progressCircular, false)
                        UiHandler.toastSuccessMessage(this, resource.data?.message.toString())
                        goToActivity(LoginActivity())
                    }

                    Status.ERROR -> {
                        UiHandler.setupVisibilityProgressBar(binding.progressCircular, false)
                        UiHandler.toastErrorMessage(this, resource.message.toString())
                    }
                }
            }
    }
}