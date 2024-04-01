package com.am.bbsa.ui.customers.account

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.am.bbsa.adapter.account.AccountCardAdapter
import com.am.bbsa.data.dummy_data.DummyData
import com.am.bbsa.databinding.FragmentAccountBinding
import com.am.bbsa.ui.auth.AuthViewModel
import com.am.bbsa.ui.auth.login.LoginActivity
import com.am.bbsa.utils.finish
import org.koin.android.ext.android.inject

class AccountFragment : Fragment() {
    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding!!
    private val authViewModel : AuthViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAccountBinding.inflate(inflater, container, false)
        setupAdapter()
        return binding.root
    }

    private fun setupAdapter() {
        val adapter = AccountCardAdapter().apply {
            submitList(DummyData.DummyDataCardAccount)
            callbackOnclick = { id ->
                when (id) {
                    4 -> {
                        Intent(
                            requireContext(),
                            LoginActivity::class.java
                        ).finish(requireActivity())
                        authViewModel.clearCredentialUser()
                    }
                }
            }
        }
        binding.cardMenuAccount.recyclerViewCardAccount.let {
            it.adapter = adapter
            it.layoutManager = LinearLayoutManager(requireContext())
        }
    }
}