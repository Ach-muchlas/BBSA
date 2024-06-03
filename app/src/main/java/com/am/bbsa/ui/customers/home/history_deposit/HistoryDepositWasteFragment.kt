package com.am.bbsa.ui.customers.home.history_deposit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.am.bbsa.R
import com.am.bbsa.adapter.home.HistoryDepositWasteNasabahAdapter
import com.am.bbsa.data.response.HistoryDepositWasteResponse
import com.am.bbsa.databinding.FragmentHistoryDepositWasteBinding
import com.am.bbsa.service.source.Status
import com.am.bbsa.ui.auth.AuthViewModel
import com.am.bbsa.ui.customers.home.HomeViewModel
import com.am.bbsa.utils.Destination
import com.am.bbsa.utils.Navigation
import com.am.bbsa.utils.UiHandler
import org.koin.android.ext.android.inject

class HistoryDepositWasteFragment : Fragment() {
    private var _binding: FragmentHistoryDepositWasteBinding? = null
    private val binding get() = _binding!!
    private val adapter = HistoryDepositWasteNasabahAdapter()
    private val viewModel: HomeViewModel by inject()
    private val authViewModel: AuthViewModel by inject()
    private val token: String by lazy { authViewModel.getCredentialUser()?.token.toString() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryDepositWasteBinding.inflate(inflater, container, false)
        initialize()
        setupNavigation()
        setupGetDataFromApi()
        return binding.root
    }

    private fun initialize() {
        UiHandler.setupVisibilityBottomNavigationNasabah(activity, true)
        binding.viewAppBar.textTitleAppBar.text = getString(R.string.history_deposit_waste)
    }

    private fun setupGetDataFromApi() {
        // get data history deposit waste
        viewModel.showHistoryDepositWaste(token).observe(viewLifecycleOwner) { resource ->
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

    private fun setupNavigation() {
        binding.viewAppBar.buttonBack.setOnClickListener {
            findNavController()
        }
    }

    private fun setupAdapter(data: HistoryDepositWasteResponse?) {
        adapter.submitList(data?.data)
        adapter.setOnclickListener { id ->
            val bundle = Bundle().apply {
                putInt(BUNDLE_ID, id)
            }
            Navigation.navigationFragment(
                Destination.HISTORY_DEPOSIT_WASTE_TO_DETAIL_HISTORY_DEPOSIT,
                findNavController(),
                bundle
            )
        }
        binding.recyclerViewHistoryDepositWaste.let {
            it.adapter = adapter
            it.layoutManager = LinearLayoutManager(requireContext())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        UiHandler.setupVisibilityBottomNavigationNasabah(activity, false)
    }

    companion object {
        const val BUNDLE_ID = "id_history_deposit"
    }
}