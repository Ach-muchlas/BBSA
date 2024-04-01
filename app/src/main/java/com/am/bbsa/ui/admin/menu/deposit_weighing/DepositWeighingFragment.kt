package com.am.bbsa.ui.admin.menu.deposit_weighing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.am.bbsa.adapter.menu.DepositWeighingAdapter
import com.am.bbsa.data.response.DepositWeighingResponse
import com.am.bbsa.databinding.FragmentDepositWeighingBinding
import com.am.bbsa.service.source.Status
import com.am.bbsa.ui.admin.menu.MenuViewModel
import com.am.bbsa.ui.auth.AuthViewModel
import com.am.bbsa.utils.Destination
import com.am.bbsa.utils.Navigation
import com.am.bbsa.utils.UiHandler
import org.koin.android.ext.android.inject

class DepositWeighingFragment : Fragment() {
    private var _binding: FragmentDepositWeighingBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MenuViewModel by inject()
    private val authViewModel: AuthViewModel by inject()

    private val token by lazy {
        authViewModel.getCredentialUser()?.token.toString()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDepositWeighingBinding.inflate(inflater, container, false)
        UiHandler.setupVisibilityBottomNavigationAdmin(activity, true)
        displayDepositWeighing()
        setupNavigation()
        return binding.root
    }

    private fun setupNavigation() {
        binding.buttonBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }


    private fun displayDepositWeighing() {
        viewModel.showAllDepositWeighing(token).observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                Status.LOADING -> {
                    UiHandler.setupVisibilityProgressBar(binding.progressBar, true)
                }

                Status.SUCCESS -> {
                    UiHandler.setupVisibilityProgressBar(binding.progressBar, false)
                    setupAdapterDepositWeighing(resource.data)
                }

                Status.ERROR -> {
                    UiHandler.setupVisibilityProgressBar(binding.progressBar, false)
                    UiHandler.toastErrorMessage(requireContext(), resource.message.toString())
                }
            }
        }
    }

    private fun setupAdapterDepositWeighing(data: DepositWeighingResponse?) {
        val adapter = DepositWeighingAdapter().apply {
            submitList(data?.data)
            callbackOnclick = { data ->
                val bundle = Bundle().apply {
                    putParcelable(KEY_DEPOSIT_WEIGHING, data)
                }
                Navigation.navigationFragment(
                    Destination.DEPOSIT_WEIGHING_TO_DETAIL_DEPOSIT_WEIGHING,
                    findNavController(),
                    bundle
                )
            }
        }
        binding.recyclerViewDepositWeighing.let {
            it.adapter = adapter
            it.layoutManager = LinearLayoutManager(requireContext())
        }
    }

    companion object {
        const val KEY_DEPOSIT_WEIGHING = "key_deposit_weighing"
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        UiHandler.setupVisibilityBottomNavigationAdmin(activity, false)
    }

}