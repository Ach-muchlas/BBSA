package com.am.bbsa.ui.customers.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.am.bbsa.R
import com.am.bbsa.adapter.home.NewsAdapter
import com.am.bbsa.data.response.NewsResponse
import com.am.bbsa.data.response.UserResponse
import com.am.bbsa.databinding.FragmentHomeBinding
import com.am.bbsa.service.source.Status
import com.am.bbsa.ui.auth.AuthViewModel
import com.am.bbsa.utils.Destination
import com.am.bbsa.utils.Formatter
import com.am.bbsa.utils.Navigation.navigationFragment
import com.am.bbsa.utils.UiHandler
import com.bumptech.glide.Glide
import org.koin.android.ext.android.inject

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by inject()
    private val authViewModel: AuthViewModel by inject()
    private val token by lazy {
        authViewModel.getCredentialUser()?.token.toString()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        setupNavigation()
        displayHome()
        displayNews()
        return binding.root
    }

    private fun displayNews() {
        viewModel.showNews(token).observe(viewLifecycleOwner) { resource ->
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

    private fun displayHome() {
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
                    UiHandler.toastErrorMessage(requireContext(), resource.message.toString())
                }
            }
        }
    }

    private fun setupViewCredentialUser(data: UserResponse?) {
        Glide.with(requireContext()).load(data?.data?.fotoProfil)
            .into(binding.viewAppBar.imageProfile)
        binding.viewAppBar.textName.text = data?.data?.name
        binding.cardBalance.textBalance.text =
            Formatter.formatCurrency(data?.data?.nasabah?.balance ?: 0)
        binding.cardBalance.textValuePredict.text =
            Formatter.formatCurrency(data?.data?.nasabah?.temporaryBalance ?: 0)
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

    private fun setupNavigation() {
        binding.menuDeposit.cardMenu.setOnClickListener {
            navigationFragment(Destination.HOME_TO_DEPOSIT_WASTE, findNavController())
        }
        /*menu withdraw*/
        binding.menuWithdrawBalance.apply {
            iconMenu.setImageResource(R.drawable.icon_widrawal_balance)
            textTitleMenu.text = getString(R.string.withdraw_balance)
            cardMenu.setOnClickListener {
                navigationFragment(Destination.HOME_T0_WITHDRAW_BALANCE, findNavController())
            }
        }

        /*menu pickup*/
        binding.menuPickUpWaste.apply {
            iconMenu.setImageResource(R.drawable.icon_trash_pickup)
            textTitleMenu.text = getString(R.string.pick_up_waste)
            cardMenu.setOnClickListener {
                navigationFragment(Destination.HOME_TO_PICKUP_WASTE, findNavController())
            }
        }

        /*menu history deposit waste*/
        binding.menuHistoryDeposit.apply {
            iconMenu.setImageResource(R.drawable.icon_waste_deposit_history)
            textTitleMenu.text = getString(R.string.history_deposit)
            cardMenu.setOnClickListener {
                navigationFragment(Destination.HOME_TO_HISTORY_DEPOSIT, findNavController())
            }
        }

        /*menu data waste*/
        binding.menuTypeWaste.apply {
            iconMenu.setImageResource(R.drawable.icon_type_waste)
            textTitleMenu.text = getString(R.string.type_waste)
            cardMenu.setOnClickListener {
                navigationFragment(Destination.HOME_TO_TYPE_WASTE, findNavController())
            }
        }
    }

    private fun setupAdapter(data: NewsResponse?) {
        val newsAdapter = NewsAdapter().apply {
            submitList(data?.data)
        }
        binding.recyclerViewNews.let {
            it.layoutManager = LinearLayoutManager(requireContext())
            it.adapter = newsAdapter
        }
    }
}