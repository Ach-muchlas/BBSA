package com.am.bbsa.ui.customers.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.am.bbsa.R
import com.am.bbsa.databinding.ActivityCustomersMainBinding

class CustomersMainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCustomersMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCustomersMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupBottomNavigation()
    }

    private fun setupBottomNavigation() {
        val navController = Navigation.findNavController(this, R.id.navigationHost)
        val navigationView = binding.bottomNavigation
        navigationView.setupWithNavController(navController)
    }
}