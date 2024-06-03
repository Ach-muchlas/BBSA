package com.am.bbsa.ui.customers.detail_balance
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.am.bbsa.R
import com.am.bbsa.adapter.balance.HistoryWithdrawBalanceAdapter
import com.am.bbsa.data.response.UserResponse
import com.am.bbsa.data.response.WithdrawBalanceResponse
import com.am.bbsa.databinding.FragmentDetailBalanceBinding
import com.am.bbsa.service.source.Status
import com.am.bbsa.ui.auth.AuthViewModel
import com.am.bbsa.ui.customers.home.HomeViewModel
import com.am.bbsa.utils.Destination
import com.am.bbsa.utils.Formatter
import com.am.bbsa.utils.KEY
import com.am.bbsa.utils.Navigation
import com.am.bbsa.utils.UiHandler
import com.bumptech.glide.Glide
import org.koin.android.ext.android.inject

class DetailBalanceFragment : Fragment() {
    private var _binding: FragmentDetailBalanceBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by inject()
    private val authViewModel: AuthViewModel by inject()

    private val token: String by lazy { authViewModel.getCredentialUser()?.token.toString() }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBalanceBinding.inflate(inflater, container, false)
        initialize()
        setupNavigation()
        setupGetDataFromApi()
        return binding.root
    }

    private fun initialize() {
        UiHandler.setupVisibilityBottomNavigationNasabah(activity, true)
        binding.viewAppBar.textTitleAppBar.text = getString(R.string.balance)
    }

    private fun setupNavigation() {
        binding.viewAppBar.buttonBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setupGetDataFromApi() {
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

        viewModel.showWithdrawBalance(token).observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                Status.LOADING -> {}
                Status.SUCCESS -> {
                    setupHistoryAdapter(resource.data)
                }

                Status.ERROR -> {
                    UiHandler.toastErrorMessage(requireContext(), resource.message.toString())
                }
            }
        }

    }

    private fun setupViewCredentialUser(data: UserResponse?) {
        with(binding.cardBalance) {
            Glide.with(requireContext()).load(data?.data?.fotoProfil).into(imageProfile)
            textName.text = data?.data?.name
            textPhone.text = data?.data?.nomorTelephone
            textValueBalance.text = Formatter.formatCurrency(data?.data?.nasabah?.balance ?: 0)
            textValuePredict.text =
                Formatter.formatCurrency(data?.data?.nasabah?.temporaryBalance ?: 0)
        }
    }

    private fun setupHistoryAdapter(data: WithdrawBalanceResponse?) {
        val adapter = HistoryWithdrawBalanceAdapter().apply {
            submitList(data?.data)
            setOnClickListener { id ->
                val bundle = Bundle().apply { putInt(KEY.BUNDLE_ID, id) }
                Navigation.navigationFragment(
                    Destination.DETAIL_BALANCE_TO_DETAIL_HISTORY_WITHDRAW_BALANCE,
                    findNavController(),
                    bundle
                )
            }
        }
        binding.recyclerViewHistory.let {
            it.adapter = adapter
            it.layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun showShimmer(isVisible: Boolean) {
        with(binding.cardBalance) {
            UiHandler.manageShimmer(shimmerContainerPhotoProfile, isVisible)
            UiHandler.manageShimmer(shimmerContainerName, isVisible)
            UiHandler.manageShimmer(shimmerContainerPhoneNumber, isVisible)
            UiHandler.manageShimmer(shimmerContainerBalance, isVisible)
            UiHandler.manageShimmer(shimmerContainerPredict, isVisible)
        }
    }

    private fun setupIsVisibilityView() {
        binding.cardBalance.apply {
            imageProfile.visibility = View.VISIBLE
            textName.visibility = View.VISIBLE
            textPhone.visibility = View.VISIBLE
            textValueBalance.visibility = View.VISIBLE
            textValuePredict.visibility = View.VISIBLE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        UiHandler.setupVisibilityBottomNavigationNasabah(activity, false)
    }
}