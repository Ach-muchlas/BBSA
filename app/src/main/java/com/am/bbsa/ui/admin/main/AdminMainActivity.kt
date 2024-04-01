package com.am.bbsa.ui.admin.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.am.bbsa.R
import com.am.bbsa.databinding.ActivityAdminMainBinding
import com.am.bbsa.ui.auth.AuthViewModel
import com.am.bbsa.ui.auth.login.LoginActivity
import com.am.bbsa.ui.customers.main.CustomersMainActivity
import com.am.bbsa.utils.finish
import org.koin.android.ext.android.inject

class AdminMainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdminMainBinding
    private val authViewModel: AuthViewModel by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupNavigation()
        setupBottomNavigation()
    }

    private fun setupNavigation() {
        if (!authViewModel.isLogin() || authViewModel.isSessionExpired()) {
            authViewModel.clearCredentialUser()
            Intent(this, LoginActivity::class.java).finish(this)
        } else if (!authViewModel.isAdminUser()) {
            Intent(this, CustomersMainActivity::class.java).finish(this)
        }
    }

    private fun setupBottomNavigation() {
        val navController = Navigation.findNavController(this, R.id.navigationHost)
        val bottomNav = binding.bottomNavigationAdmin
        bottomNav.setupWithNavController(navController)
    }
}