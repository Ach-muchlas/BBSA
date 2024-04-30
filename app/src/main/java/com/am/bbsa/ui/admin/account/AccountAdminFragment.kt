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
import com.am.bbsa.data.response.UserResponse
import com.am.bbsa.databinding.FragmentAccountBinding
import com.am.bbsa.service.source.Status
import com.am.bbsa.ui.auth.AuthViewModel
import com.am.bbsa.ui.auth.login.LoginActivity
import com.am.bbsa.ui.customers.account.AccountViewModel
import com.am.bbsa.utils.Destination
import com.am.bbsa.utils.Navigation
import com.am.bbsa.utils.UiHandler
import com.am.bbsa.utils.finish
import com.bumptech.glide.Glide
import org.koin.android.ext.android.inject

class AccountAdminFragment : Fragment() {
    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding!!
    private val authViewModel: AuthViewModel by inject()
    private val viewModel: AccountViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAccountBinding.inflate(inflater, container, false)
        setupAdapter()
        setupView()
        return binding.root
    }

    private fun setupView() {
        val token = authViewModel.getCredentialUser()?.token.toString()
        viewModel.showDataUser(token).observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                Status.LOADING -> {
                    showShimmer(true)
                }

                Status.SUCCESS -> {
                    showShimmer(false)
                    setupIsVisibilityView()
                    setupViewCredentialUser(resource.data)
                }

                Status.ERROR -> {
                    showShimmer(false)
                    setupIsVisibilityView()
                    UiHandler.toastErrorMessage(requireContext(), resource.message.toString())
                }
            }
        }
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

    private fun setupIsVisibilityView() {
        binding.viewAppBar.apply {
            textName.visibility = View.VISIBLE
            imageProfile.visibility = View.VISIBLE
        }
    }

    private fun setupViewCredentialUser(data: UserResponse?) {
        Glide.with(requireContext()).load(data?.data?.fotoProfil)
            .into(binding.viewAppBar.imageProfile)
        binding.viewAppBar.textHai.text = data?.data?.name
        binding.viewAppBar.textName.text = data?.data?.nomorTelephone
    }

    private fun showShimmer(isVisible: Boolean) {
        with(binding.viewAppBar) {
            UiHandler.manageShimmer(shimmerContainerImage, isVisible)
            UiHandler.manageShimmer(shimmerContainerName, isVisible)
        }
    }
}