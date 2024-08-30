package com.am.bbsa.ui.admin.menu.history_withdraw_balance

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.am.bbsa.R
import com.am.bbsa.adapter.menu.HistoryWithdrawBalanceAdminAdapter
import com.am.bbsa.data.response.HistoryWithdrawBalanceResponse
import com.am.bbsa.databinding.FragmentHistoryWithdrawBalanceAdminBinding
import com.am.bbsa.service.source.Status
import com.am.bbsa.ui.admin.menu.MenuViewModel
import com.am.bbsa.ui.auth.AuthViewModel
import com.am.bbsa.utils.Destination
import com.am.bbsa.utils.KEY
import com.am.bbsa.utils.Navigation
import com.am.bbsa.utils.UiHandler
import org.koin.android.ext.android.inject

class HistoryWithdrawBalanceAdminFragment : Fragment() {
    private var _binding: FragmentHistoryWithdrawBalanceAdminBinding? = null
    private val binding get() = _binding!!
    private val authViewModel: AuthViewModel by inject()
    private val adapter = HistoryWithdrawBalanceAdminAdapter()
    private val viewModel: MenuViewModel by inject()
    private val token: String by lazy { authViewModel.getCredentialUser()?.token.toString() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryWithdrawBalanceAdminBinding.inflate(inflater, container, false)
        initialize()
        setupGetDataFromApi()
        setupNavigation()
        return binding.root
    }

    private fun initialize() {
        UiHandler.setupVisibilityBottomNavigationAdmin(activity, true)
        binding.viewAppbar.textTitleAppBar.text = getString(R.string.history_withdrawal)
    }

    private fun setupNavigation() {
        binding.viewAppbar.buttonBack.setOnClickListener { findNavController().popBackStack() }
        adapter.setOnclickListener { id ->
            val bundle = Bundle().apply {
                putInt(KEY.BUNDLE_ID, id)
            }
            Navigation.navigationFragment(
                Destination.HISTORY_WITHDRAW_BALANCE_ADMIN_TO_DETAIL_HISTORY_WITHDRAW_BALANCE,
                findNavController(),
                bundle
            )

        }

    }

    private fun setupGetDataFromApi() {
        viewModel.showAllHistoryWithdrawNasabahBalance(token)
            .observe(viewLifecycleOwner) { resource ->
                when (resource.status) {
                    Status.LOADING -> {}
                    Status.SUCCESS -> {
                        setupAdapter(resource.data)
                    }

                    Status.ERROR -> {
                        UiHandler.toastErrorMessage(requireContext(), resource.message.toString())
                    }
                }
            }
    }

    private fun setupAdapter(data: HistoryWithdrawBalanceResponse?) {
        adapter.submitList(data?.data)
        binding.recyclerView.let {
            it.adapter = adapter
            it.layoutManager = LinearLayoutManager(requireContext())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        UiHandler.setupVisibilityBottomNavigationAdmin(activity, false)
    }
}