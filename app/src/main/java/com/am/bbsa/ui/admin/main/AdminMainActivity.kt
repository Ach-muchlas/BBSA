package com.am.bbsa.ui.admin.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.am.bbsa.R
import com.am.bbsa.databinding.ActivityAdminMainBinding

class AdminMainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdminMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupBottomNavigation()
    }

    private fun setupBottomNavigation() {
        val navController = Navigation.findNavController(this, R.id.navigationHost)
        val bottomNav = binding.bottomNavigationAdmin
        bottomNav.setupWithNavController(navController)
    }
}