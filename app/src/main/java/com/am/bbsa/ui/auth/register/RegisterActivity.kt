package com.am.bbsa.ui.auth.register
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.am.bbsa.R
import com.am.bbsa.data.response.DataItemRegister
import com.am.bbsa.databinding.ActivityRegisterBinding
import com.am.bbsa.service.source.Status
import com.am.bbsa.ui.auth.AuthViewModel
import com.am.bbsa.ui.auth.login.LoginActivity
import com.am.bbsa.ui.auth.otp.OtpRegisterActivity
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
        setupDropdown()
        setupView()
        setupErrorEditText()
        setupNavigation()
        setContentView(binding.root)
    }

    private fun setupView() {
        UiHandler.setHintBehavior(
            binding.edlFullName,
            binding.edlNIK,
            binding.edlAddress,
            binding.edlGender,
            binding.edlPassword,
            binding.edlNumberPhone
        )
    }

    private fun setupErrorEditText() {
        binding.edtNIK.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val length = text?.length ?: 0
                when {
                    length < 16 -> {
                        val remaining = 16 - length
                        binding.edtNIK.error = "NIK kurang $remaining digit!!"
                    }

                    length > 16 -> {
                        val excess = length - 16
                        binding.edtNIK.error = "NIK kelebihan $excess digit!!"
                    }

                    else -> {
                        binding.edtNIK.error = null
                    }
                }
            }

            override fun afterTextChanged(p0: Editable?) {}
        })
    }

    private fun setupDropdown() {
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
        }
    }

    private fun register() {
        /*menampung data edit text di variabel*/
        val name = binding.edtFullName.text.toString()
        val noNIK = binding.edtNIK.text.toString()
        val noTelephone = binding.edtNumberPhone.text.toString()
        val password = binding.edtPassword.text.toString()
        val address = binding.edtAddress.text.toString()
        if (!validateField(name, "Name")) {
            return
        } else if (!validateField(noNIK, "NIK")) {
            return
        } else if (!validateField(noTelephone, "Nomor Telephone")) {
            return
        } else if (!validateField(password, "Password")) {
            return
        } else if (!validateField(address, "Alamat")) {
            return
        } else if(!UiHandler.validatePassword(password, this)){
            return
        }

        viewModel.register(
            name,
            noNIK,
            selectedGender.toString(),
            address,
            noTelephone,
            password
        )
            .observe(this@RegisterActivity)
            { resource ->
                when (resource.status) {
                    Status.LOADING -> {
                        UiHandler.setupVisibilityProgressBar(binding.progressCircular, true)
                    }

                    Status.SUCCESS -> {
                        UiHandler.setupVisibilityProgressBar(binding.progressCircular, false)
                        UiHandler.toastSuccessMessage(this, resource.data?.message.toString())
                        val data: DataItemRegister? = resource.data?.data
                        viewModel.saveCredentialRegister(data!!)
                        goToActivity(OtpRegisterActivity())
                    }

                    Status.ERROR -> {
                        UiHandler.setupVisibilityProgressBar(binding.progressCircular, false)
                        UiHandler.toastErrorMessage(this, resource.message.toString())
                    }
                }
            }
    }

    private fun validateField(field: String, fieldName: String): Boolean {
        if (field.isEmpty()) {
            UiHandler.toastErrorMessage(this, "$fieldName tidak boleh kosong.")
            return false
        }
        return true
    }

}