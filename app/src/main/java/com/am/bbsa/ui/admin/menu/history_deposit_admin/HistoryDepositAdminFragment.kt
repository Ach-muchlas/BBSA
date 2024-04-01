package com.am.bbsa.ui.admin.menu.history_deposit_admin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.am.bbsa.adapter.menu.HistoryDepositAdapter
import com.am.bbsa.data.response.HistoryDepositResponse
import com.am.bbsa.databinding.FragmentHistoryDepositAdminBinding
import com.am.bbsa.service.source.Status
import com.am.bbsa.ui.admin.menu.MenuViewModel
import com.am.bbsa.ui.auth.AuthViewModel
import com.am.bbsa.utils.UiHandler
import org.koin.android.ext.android.inject

class HistoryDepositAdminFragment : Fragment() {
    private var _binding: FragmentHistoryDepositAdminBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MenuViewModel by inject()
    private val authViewModel: AuthViewModel by inject()
    private val token by lazy {
        authViewModel.getCredentialUser()?.token.toString()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryDepositAdminBinding.inflate(inflater, container, false)
        UiHandler.setupVisibilityBottomNavigationAdmin(activity, true)
        displayHistory()
        setupNavigation()
        return binding.root
    }

    private fun setupNavigation() {
        binding.buttonBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun displayHistory() {
        viewModel.showAllHistoryDeposit(token).observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                Status.LOADING -> {}
                Status.SUCCESS -> {
                    setupAdapter(resource.data)
                }

                Status.ERROR -> {
                    UiHandler.toastErrorMessage(requireContext(), resource.data?.message.toString())
                }
            }
        }
    }

    private fun setupAdapter(data: HistoryDepositResponse?) {
        val adapter = HistoryDepositAdapter().apply {
            submitList(data?.data)
        }
        binding.recyclerViewHistoryDeposit.let {
            it.adapter = adapter
            it.layoutManager = LinearLayoutManager(requireContext())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        UiHandler.setupVisibilityBottomNavigationAdmin(activity, false)
    }
}