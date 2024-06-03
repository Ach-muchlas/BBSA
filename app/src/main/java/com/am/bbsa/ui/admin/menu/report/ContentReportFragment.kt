package com.am.bbsa.ui.admin.menu.report

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.am.bbsa.adapter.menu.report.NasabahDepositWasteReportsAdapter
import com.am.bbsa.adapter.menu.report.NasabahWithdrawBalanceReportsAdapter
import com.am.bbsa.data.response.DetailNasabahResponse
import com.am.bbsa.data.response.report.DataItemNasabahWasteDepositReport
import com.am.bbsa.data.response.report.NasabahWasteDepositReportsResponse
import com.am.bbsa.data.response.report.NasabahWithdrawBalanceReportsResponse
import com.am.bbsa.databinding.FragmentContentReportBinding
import com.am.bbsa.service.source.Status
import com.am.bbsa.ui.admin.menu.MenuViewModel
import com.am.bbsa.ui.auth.AuthViewModel
import com.am.bbsa.utils.Formatter
import com.am.bbsa.utils.UiHandler
import org.koin.android.ext.android.inject

class ContentReportFragment : Fragment() {
    private var _binding: FragmentContentReportBinding? = null
    private val binding get() = _binding!!
    private val authViewModel: AuthViewModel by inject()
    private val viewModel: MenuViewModel by inject()
    private val token: String by lazy { authViewModel.getCredentialUser()?.token.toString() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContentReportBinding.inflate(inflater, container, false)
        setupGetDataFromApi()
        return binding.root
    }

    private fun setupGetDataFromApi() {
        viewModel.showReportDepositWasteNasabah(token, 3).observe(viewLifecycleOwner) { resource ->
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

        viewModel.showReportWithdrawBalanceNasabah(token, 3).observe(viewLifecycleOwner){resource ->
            when(resource.status){
                Status.LOADING -> {}
                Status.SUCCESS -> {
                    setupAdapterWithdrawBalance(resource.data)
                }

                Status.ERROR -> {
                    UiHandler.toastErrorMessage(requireContext(), resource.message.toString())
                    Log.e("Check", "error : ${resource.message.toString()}")
                }
            }
        }

        viewModel.showDetailNasabahById(3-1, token).observe(viewLifecycleOwner){resource ->
            when(resource.status){
                Status.LOADING -> {}
                Status.SUCCESS -> {
                    setupView(resource.data)
                }

                Status.ERROR -> {
                    UiHandler.toastErrorMessage(requireContext(), resource.message.toString())
                    Log.e("Check", "error : ${resource.message.toString()}")
                }
            }
        }
    }

    private fun setupView(data : DetailNasabahResponse?){
        binding.textValueName.text = data?.data?.user?.name
        binding.textValueNoTelephone.text = data?.data?.user?.phoneNumber
        binding.textValueNoRegis.text = data?.data?.noRegis
        binding.textValueBalance.text = Formatter.formatCurrency(data?.data?.balance ?: 0)
    }

    private fun setupAdapter(data: NasabahWasteDepositReportsResponse?) {
        val adapter =
            NasabahDepositWasteReportsAdapter(data?.data as List<DataItemNasabahWasteDepositReport>)
        binding.recyclerViewDepositWaste.let {
            it.adapter = adapter
            it.layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun setupAdapterWithdrawBalance(data: NasabahWithdrawBalanceReportsResponse?) {
        val adapter = NasabahWithdrawBalanceReportsAdapter().apply { submitList(data?.data) }
        binding.recyclerViewWithdrawBalance.let {
            it.adapter = adapter
            it.layoutManager = LinearLayoutManager(requireContext())
        }
    }
}