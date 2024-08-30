package com.am.bbsa.ui.customers.transaction_history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.am.bbsa.adapter.history_transaction.HistoryTransactionAdapter
import com.am.bbsa.data.response.HistoryTransactionResponse
import com.am.bbsa.databinding.FragmentHistoryTransactionBinding
import com.am.bbsa.service.source.Status
import com.am.bbsa.ui.auth.AuthViewModel
import com.am.bbsa.ui.customers.home.HomeViewModel
import com.am.bbsa.utils.UiHandler
import org.koin.android.ext.android.inject

class HistoryTransactionFragment : Fragment() {
    private var _binding: FragmentHistoryTransactionBinding? = null
    private val binding get() = _binding!!
    private val authViewModel: AuthViewModel by inject()
    private val viewModel: HomeViewModel by inject()
    private val token: String by lazy { authViewModel.getCredentialUser()?.token.toString() }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryTransactionBinding.inflate(inflater, container, false)
        setupGetDataFromApi()
        return binding.root
    }

    private fun setupGetDataFromApi() {
        viewModel.showHistoryTransaction(token).observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                Status.LOADING -> {
                    showShimmer(true)
                }

                Status.SUCCESS -> {
                    showShimmer(false)
                    setupAdapter(resource.data)
                }

                Status.ERROR -> {
                    showShimmer(false)
                    UiHandler.toastErrorMessage(requireContext(), resource.message.toString())
                }
            }
        }
    }

    private fun setupAdapter(data: HistoryTransactionResponse?) {
        val adapter = HistoryTransactionAdapter().apply {
            submitList(data?.data)
        }
        binding.recyclerViewHistory.let {
            it.adapter = adapter
            it.layoutManager = LinearLayoutManager(requireContext())
        }

        if (data?.data.isNullOrEmpty()){
            binding.textResult.visibility = View.VISIBLE
        }
    }

    private fun showShimmer(isVisible: Boolean) {
        UiHandler.manageShimmer(binding.shimmerContainer1, isVisible)
        UiHandler.manageShimmer(binding.shimmerContainer2, isVisible)
        UiHandler.manageShimmer(binding.shimmerContainer3, isVisible)
        UiHandler.manageShimmer(binding.shimmerContainer4, isVisible)
        UiHandler.manageShimmer(binding.shimmerContainer5, isVisible)
    }
}