package com.am.bbsa.ui.customers.account

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
import com.am.bbsa.data.response.DataItemUser
import com.am.bbsa.databinding.FragmentAccountBinding
import com.am.bbsa.service.source.Status
import com.am.bbsa.ui.auth.AuthViewModel
import com.am.bbsa.ui.auth.login.LoginActivity
import com.am.bbsa.utils.Destination
import com.am.bbsa.utils.Navigation
import com.am.bbsa.utils.UiHandler
import com.am.bbsa.utils.finish
import com.bumptech.glide.Glide
import org.koin.android.ext.android.inject

class AccountFragment : Fragment() {
    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding!!
    private val authViewModel: AuthViewModel by inject()
    private val viewModel: AccountViewModel by inject()
    private val token by lazy {
        authViewModel.getCredentialUser()?.token.toString()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAccountBinding.inflate(inflater, container, false)
        setupAdapter()
        displayAccount()
        return binding.root
    }

    private fun displayAccount() {
        viewModel.showDataUser(token).observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                Status.LOADING -> {
                    showShimmer(true)
                }

                Status.SUCCESS -> {
                    showShimmer(false)
                    setupIsVisibilityView()
                    setupViewCredentialUser(resource.data?.data)
                }

                Status.ERROR -> {
                    showShimmer(false)
                    UiHandler.toastErrorMessage(requireContext(), resource.message.toString())
                }
            }
        }
    }

    private fun setupIsVisibilityView() {
        with(binding.viewAppBar) {
            textName.visibility = View.VISIBLE
            imageProfile.visibility = View.VISIBLE
        }
    }

    private fun setupViewCredentialUser(data: DataItemUser?) {
        binding.viewAppBar.textHai.text = data?.name.toString()
        binding.viewAppBar.textName.text = data?.nomorTelephone.toString()
        Glide.with(requireContext()).load(data?.fotoProfil).into(binding.viewAppBar.imageProfile)
    }

    private fun showShimmer(isVisible: Boolean) {
        with(binding.viewAppBar) {
            UiHandler.manageShimmer(shimmerContainerImage, isVisible)
            UiHandler.manageShimmer(shimmerContainerName, isVisible)
        }
    }


    private fun setupAdapter() {
        val adapter = AccountCardAdapter().apply {
            submitList(DummyData.DummyDataCardAccount)
            callbackOnclick = { id ->
                when (id) {
                    1 -> {
                        Navigation.navigationFragment(
                            Destination.ACCOUNT_TO_PROFILE,
                            findNavController()
                        )
                    }

                    2 -> {
                        Navigation.navigationFragment(
                            Destination.ACCOUNT_TO_CHANGE_PASSWORD,
                            findNavController()
                        )
                    }

                    3 -> {
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