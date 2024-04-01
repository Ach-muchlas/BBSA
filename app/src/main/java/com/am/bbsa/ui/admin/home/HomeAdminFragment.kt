package com.am.bbsa.ui.admin.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.am.bbsa.data.response.TotalSaldoResponse
import com.am.bbsa.databinding.FragmentHomeAdminBinding
import com.am.bbsa.service.source.Status
import com.am.bbsa.ui.auth.AuthViewModel
import com.am.bbsa.utils.Formatter
import com.am.bbsa.utils.UiHandler
import org.koin.android.ext.android.inject

class HomeAdminFragment : Fragment() {
    private var _binding: FragmentHomeAdminBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeAdminViewModel by inject()
    private val authViewModel: AuthViewModel by inject()
    private val token by lazy {
        authViewModel.getCredentialUser()?.token.toString()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeAdminBinding.inflate(inflater, container, false)
        displayView()
        return binding.root
    }

    private fun displayView() {
        viewModel.showAllActualBalance(token).observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                Status.LOADING -> {
                    UiHandler.manageShimmer(binding.cardBalance.shimmerContainerBalance, true)
                }

                Status.SUCCESS -> {
                    UiHandler.manageShimmer(binding.cardBalance.shimmerContainerBalance, false)
                    binding.cardBalance.textBalance.visibility = View.VISIBLE
                    setupViewBalance(resource.data)
                }

                Status.ERROR -> {
                    UiHandler.manageShimmer(binding.cardBalance.shimmerContainerBalance, false)
                    binding.cardBalance.textBalance.visibility = View.VISIBLE
                    UiHandler.toastErrorMessage(requireContext(), resource.message.toString())
                }
            }
        }
    }

    private fun setupViewBalance(data: TotalSaldoResponse?) {
        binding.cardBalance.textBalance.text = Formatter.formatCurrency(data?.data?.totalSaldo ?: 0)
    }
}

