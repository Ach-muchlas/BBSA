package com.am.bbsa.ui.admin.menu.history_deposit_admin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.am.bbsa.R
import com.am.bbsa.adapter.menu.DetailHistoryDepositAdapter
import com.am.bbsa.data.response.DetailHistoryDepositResponse
import com.am.bbsa.databinding.FragmentDetailHistoryDepositBinding
import com.am.bbsa.service.source.Status
import com.am.bbsa.ui.admin.menu.MenuViewModel
import com.am.bbsa.ui.auth.AuthViewModel
import com.am.bbsa.utils.Formatter
import com.am.bbsa.utils.UiHandler
import org.koin.android.ext.android.inject

class DetailHistoryDepositAdminFragment : Fragment() {

    private var _binding: FragmentDetailHistoryDepositBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MenuViewModel by inject()
    private val authViewModel: AuthViewModel by inject()

    private val token: String by lazy { authViewModel.getCredentialUser()?.token.toString() }
    private val receiveIdHistoryDeposit: Int by lazy {
        arguments?.getInt(HistoryDepositAdminFragment.BUNDLE_ID) ?: 0
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailHistoryDepositBinding.inflate(inflater, container, false)
        initialize()
        setupNavigation()
        setupGetDataFromApi()
        return binding.root
    }

    private fun setupNavigation() {
        binding.viewAppbar.buttonBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }


    private fun initialize() {
        binding.viewAppbar.textTitleAppBar.text = getString(R.string.history_detail_deposit)
        UiHandler.setupVisibilityBottomNavigationAdmin(activity, true)
    }

    private fun setupGetDataFromApi() {
        viewModel.showDetailHistoryDeposit(receiveIdHistoryDeposit, token)
            .observe(viewLifecycleOwner) { resource ->
                when (resource.status) {
                    Status.LOADING -> {
                        showShimmer(true)
                    }

                    Status.SUCCESS -> {
                        showShimmer(false)
                        setupIsVisibilityView()
                        setupView(resource.data)
                        setupAdapter(resource.data)
                    }

                    Status.ERROR -> {
                        showShimmer(false)
                        setupIsVisibilityView()
                        UiHandler.toastErrorMessage(requireContext(), resource.message.toString())
                    }
                }
            }
    }

    private fun setupView(data: DetailHistoryDepositResponse?) {
        data?.data?.forEach { dataItem ->
            binding.textValueName.text = dataItem?.username.toString()
            binding.textValueDate.text = Formatter.formatDate(dataItem?.createdAt.toString())
            binding.textValueTotal.text = Formatter.formatCurrency(dataItem?.totalSetoran ?: 0)
            binding.textValueName.text = dataItem?.admin_name.toString()
        }
    }

    private fun setupIsVisibilityView() {
        binding.textValueName.visibility = View.VISIBLE
        binding.textValueTotal.visibility = View.VISIBLE
        binding.textValueDate.visibility = View.VISIBLE
        binding.textValueAdminName.visibility = View.VISIBLE
    }

    private fun showShimmer(isVisible: Boolean) {
        UiHandler.manageShimmer(binding.shimmerContainerName, isVisible)
        UiHandler.manageShimmer(binding.shimmerContainerDate, isVisible)
        UiHandler.manageShimmer(binding.shimmerContainerTotal, isVisible)
        UiHandler.manageShimmer(binding.shimmerContainerAdminName, isVisible)
    }


    private fun setupAdapter(data: DetailHistoryDepositResponse?) {
        val adapter = DetailHistoryDepositAdapter().apply {
            submitList(data?.data)
        }
        binding.recyclerViewTypeWaste.let {
            it.adapter = adapter
            it.layoutManager = LinearLayoutManager(requireContext())
        }
    }


}