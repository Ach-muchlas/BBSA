package com.am.bbsa.ui.admin.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.am.bbsa.R
import com.am.bbsa.data.response.TotalSaldoResponse
import com.am.bbsa.data.response.TotalWasteResponse
import com.am.bbsa.data.response.UserResponse
import com.am.bbsa.databinding.FragmentHomeAdminBinding
import com.am.bbsa.service.source.Status
import com.am.bbsa.ui.auth.AuthViewModel
import com.am.bbsa.utils.Formatter
import com.am.bbsa.utils.UiHandler
import com.bumptech.glide.Glide
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
        binding.cardBalance.textValuePredict.text = "10 Kg"
        displayAppBarView()
        setupView()
        return binding.root
    }

    private fun setupView() {
        with(binding.cardBalance) {
            textBalance1.text = getString(R.string.total_saldo)
            textPredict.text = getString(R.string.total_waste)
        }
    }

    private fun displayAppBarView() {
        viewModel.showTotalWaste(token).observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                Status.LOADING -> {
                    showShimmer(true)
                }

                Status.SUCCESS -> {
                    showShimmer(false)
                    setupIsVisibilityView()
                    setupViewTotalWaste(resource.data)
                }

                Status.ERROR -> {
                    Log.e("CHECK", "error : ${resource.message}")
                    UiHandler.toastErrorMessage(requireContext(), resource.message.toString())
                }
            }
        }
        // displays data credential user (photo and name)
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

        // displays data balance
        viewModel.showAllActualBalance(token).observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                Status.LOADING -> {
                    showShimmer(true)
                }

                Status.SUCCESS -> {
                    showShimmer(false)
                    setupIsVisibilityView()
                    setupViewBalance(resource.data)
                }

                Status.ERROR -> {
                    showShimmer(false)
                    setupIsVisibilityView()
                    UiHandler.toastErrorMessage(requireContext(), resource.message.toString())
                }
            }
        }
    }


    private fun setupViewCredentialUser(data: UserResponse?) {
        Glide.with(requireContext()).load(data?.data?.fotoProfil)
            .into(binding.viewAppBar.imageProfile)
        binding.viewAppBar.textName.text = data?.data?.name
    }

    private fun showShimmer(isVisible: Boolean) {
        with(binding.cardBalance) {
            UiHandler.manageShimmer(shimmerContainerBalance, isVisible)
            UiHandler.manageShimmer(shimmerContainerTemporaryBalance, isVisible)
        }
        with(binding.viewAppBar) {
            UiHandler.manageShimmer(shimmerContainerImage, isVisible)
            UiHandler.manageShimmer(shimmerContainerName, isVisible)
        }
    }

    private fun setupIsVisibilityView() {
        binding.viewAppBar.apply {
            textName.visibility = View.VISIBLE
            imageProfile.visibility = View.VISIBLE
        }
        binding.cardBalance.apply {
            textBalance.visibility = View.VISIBLE
            textValuePredict.visibility = View.VISIBLE
        }
    }

    private fun setupViewBalance(data: TotalSaldoResponse?) {
        binding.cardBalance.textBalance.text = Formatter.formatCurrency(data?.data?.totalSaldo ?: 0)
    }

    private fun setupViewTotalWaste(data: TotalWasteResponse?) {
        binding.cardBalance.textValuePredict.text =
            Formatter.formatKg(data?.data?.totalBeratSemuaSampah ?: 0)
    }
}

