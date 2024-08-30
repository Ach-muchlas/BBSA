package com.am.bbsa.ui.bottom_sheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.am.bbsa.R
import com.am.bbsa.data.response.DetailWithdrawBalanceResponse
import com.am.bbsa.databinding.FragmentSuccessWithdrawBalanceBottomSheetBinding
import com.am.bbsa.service.source.Status
import com.am.bbsa.ui.auth.AuthViewModel
import com.am.bbsa.ui.customers.home.HomeViewModel
import com.am.bbsa.utils.Formatter
import com.am.bbsa.utils.UiHandler
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.android.ext.android.inject

class SuccessWithdrawBalanceBottomSheetFragment(private val external_id: String) :
    BottomSheetDialogFragment() {

    private var _binding: FragmentSuccessWithdrawBalanceBottomSheetBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by inject()
    private val authViewModel: AuthViewModel by inject()

    private val token: String by lazy { authViewModel.getCredentialUser()?.token.toString() }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =
            FragmentSuccessWithdrawBalanceBottomSheetBinding.inflate(inflater, container, false)
        setupNavigation()
        setupGetDataDetailWithdrawBalanceFromApi()
        return binding.root
    }

    private fun setupNavigation() {
        binding.buttonOk.setOnClickListener {
            dismissNow()
        }
        binding.buttonShowStatus.setOnClickListener {
            setupGetDataDetailWithdrawBalanceFromApi()
        }
    }

    private fun setupGetDataDetailWithdrawBalanceFromApi() {
        viewModel.showDetailWithdrawBalance(token, external_id)
            .observe(viewLifecycleOwner) { resource ->
                when (resource.status) {
                    Status.LOADING -> {}
                    Status.SUCCESS -> {
                        setupView(resource.data)
                    }

                    Status.ERROR -> {
                        UiHandler.toastErrorMessage(requireContext(), resource.message.toString())
                    }
                }
            }
    }

    private fun setupView(data: DetailWithdrawBalanceResponse?) {
        val textMethod = getString(R.string.method_withdraw)

        if (data?.data?.status == "COMPLETED") {
            binding.iconSuccess.setImageResource(R.drawable.icon_success)
        } else if (data?.data?.status == "PENDING") {
            binding.iconSuccess.setImageResource(R.drawable.icon_waiting)
        } else {
            binding.iconSuccess.setImageResource(R.drawable.icon_reject)
        }

        binding.textTotal.text = Formatter.formatCurrency(data?.data?.totalPenarikan ?: 0)
        binding.textMethod.text = buildString {
            append(textMethod)
            append(" ")
            append(data?.data?.metodePenarikan)
        }
        binding.textStatus.text = buildString {
            append("Status : ")
            append(data?.data?.status)
            append(" ")
            append(data?.data?.failure_code ?: "")
        }
    }

}