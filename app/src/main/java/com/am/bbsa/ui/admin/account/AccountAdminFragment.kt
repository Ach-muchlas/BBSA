package com.am.bbsa.ui.admin.account

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.am.bbsa.adapter.account.AccountCardAdapter
import com.am.bbsa.data.dummy_data.DummyData
import com.am.bbsa.databinding.FragmentAccountBinding
import com.am.bbsa.ui.auth.AuthViewModel
import com.am.bbsa.ui.auth.login.LoginActivity
import com.am.bbsa.utils.Destination
import com.am.bbsa.utils.Navigation
import com.am.bbsa.utils.finish
import org.koin.android.ext.android.inject

class AccountAdminFragment : Fragment() {
    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding!!
    private val authViewModel: AuthViewModel by inject()

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
        }
        adapter.callbackOnclick = { id ->
            when (id) {
                1 -> Navigation.navigationFragment(
                    Destination.ACCOUNT_ADMIN_TO_PROFILE_ADMIN,
                    findNavController()
                )

                2 -> Navigation.navigationFragment(
                    Destination.ACCOUNT_ADMIN_TO_CHANGE_PASSWORD,
                    findNavController()
                )

                3 -> Navigation.navigationFragment(
                    Destination.ACCOUNT_ADMIN_TO_CHANGE_LANGUAGE,
                    findNavController()
                )

                4 -> {
                    Intent(requireContext(), LoginActivity::class.java).finish(requireActivity())
                    authViewModel.clearCredentialUser()
                }
            }
        }

        binding.cardMenuAccount.recyclerViewCardAccount.let {
            it.adapter = adapter
            it.layoutManager = LinearLayoutManager(requireContext())
        }
    }
}