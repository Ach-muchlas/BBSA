package com.am.bbsa.ui.admin.menu.history_withdraw_balance

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.am.bbsa.R
import com.am.bbsa.data.response.DetailHistoryWithdrawBalanceResponse
import com.am.bbsa.databinding.FragmentDetailHistoryWithdrawBalanceBinding
import com.am.bbsa.service.source.Status
import com.am.bbsa.ui.admin.menu.MenuViewModel
import com.am.bbsa.ui.auth.AuthViewModel
import com.am.bbsa.ui.customers.detail_balance.DetailBalanceFragment
import com.am.bbsa.utils.Formatter
import com.am.bbsa.utils.KEY
import com.am.bbsa.utils.UiHandler
import org.koin.android.ext.android.inject

class DetailHistoryWithdrawBalanceAdminFragment : Fragment() {
    private var _binding: FragmentDetailHistoryWithdrawBalanceBinding? = null
    private val binding get() = _binding!!
    private val authViewModel: AuthViewModel by inject()
    private val viewModel: MenuViewModel by inject()
    private val token: String by lazy { authViewModel.getCredentialUser()?.token.toString() }
    private val receiveId: Int by lazy { arguments?.getInt(KEY.BUNDLE_ID) ?: 0 }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailHistoryWithdrawBalanceBinding.inflate(inflater, container, false)
        initialize()
        setupNavigation()
        setupGetDataFromApi()
        return binding.root
    }

    private fun initialize() {
        UiHandler.setupVisibilityBottomNavigationNasabah(activity, true)
        binding.viewAppbar.textTitleAppBar.text =
            getString(R.string.detail_history_withdraw_balance)
    }

    private fun setupNavigation() {
        binding.viewAppbar.buttonBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setupGetDataFromApi() {
        viewModel.showDetailHistoryWithdrawBalance(token, receiveId)
            .observe(viewLifecycleOwner) { resource ->
                when (resource.status) {
                    Status.LOADING -> {
                        showShimmer(true)
                    }

                    Status.SUCCESS -> {
                        showShimmer(false)
                        setupIsVisibilityView()
                        setupView(resource.data)
                    }

                    Status.ERROR -> {
                        showShimmer(false)
                        setupIsVisibilityView()
                        UiHandler.toastErrorMessage(requireContext(), resource.message.toString())
                    }
                }
            }
    }

    private fun setupView(data: DetailHistoryWithdrawBalanceResponse?) {
        data?.data?.let {
            binding.textValueNasabahName.text = it.user?.name.toString()
            binding.textValueName.text = it.namaPemilikAkun.toString()
            binding.textValueMethod.text = it.metodePenarikan.toString()
            binding.textValueTotal.text = Formatter.formatCurrency(it.totalPenarikan ?: 0)
            binding.textValueNumberAccount.text = it.nomorRekening

            when (it.status) {
                "COMPLETED" -> {
                    binding.iconSuccess.setImageResource(R.drawable.icon_success)
                    binding.textStatus.text = "Status pengiriman berhasil"
                    binding.textValueFailureCode.text = "Berhasil"
                }

                "PENDING" -> {
                    binding.iconSuccess.setImageResource(R.drawable.icon_waiting)
                    binding.textStatus.text = "Menunggu..."
                    binding.textValueFailureCode.text = "Menunggu"
                }

                else -> {
                    binding.iconSuccess.setImageResource(R.drawable.icon_reject)
                    binding.textStatus.text = "Status pengiriman gagal"
                    binding.textValueFailureCode.text = it.failureCode
                }
            }
        }
    }


    private fun showShimmer(isVisible: Boolean) {
        UiHandler.manageShimmer(binding.shimmerContainerIcon, isVisible)
        UiHandler.manageShimmer(binding.shimmerContainerStatus, isVisible)
        UiHandler.manageShimmer(binding.shimmerContainerNasabahName, isVisible)
        UiHandler.manageShimmer(binding.shimmerContainerName, isVisible)
        UiHandler.manageShimmer(binding.shimmerContainerMethod, isVisible)
        UiHandler.manageShimmer(binding.shimmerContainerTotal, isVisible)
        UiHandler.manageShimmer(binding.shimmerContainerFailureCode, isVisible)
        UiHandler.manageShimmer(binding.shimmerContainerNumberAccount, isVisible)
    }

    private fun setupIsVisibilityView() {
        binding.iconSuccess.visibility = View.VISIBLE
        binding.textStatus.visibility = View.VISIBLE
        binding.textValueNasabahName.visibility = View.VISIBLE
        binding.textValueName.visibility = View.VISIBLE
        binding.textValueNumberAccount.visibility = View.VISIBLE
        binding.textValueTotal.visibility = View.VISIBLE
        binding.textValueMethod.visibility = View.VISIBLE
        binding.textValueFailureCode.visibility = View.VISIBLE
    }
}