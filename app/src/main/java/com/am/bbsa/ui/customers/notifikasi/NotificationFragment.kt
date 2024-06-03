package com.am.bbsa.ui.customers.notifikasi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.am.bbsa.adapter.notification.NotificationAdapter
import com.am.bbsa.data.response.NotificationResponse
import com.am.bbsa.databinding.FragmentNotificationBinding
import com.am.bbsa.service.source.Status
import com.am.bbsa.ui.auth.AuthViewModel
import com.am.bbsa.utils.NotificationHelper
import com.am.bbsa.utils.UiHandler
import org.koin.android.ext.android.inject

class NotificationFragment : Fragment() {
    private var _binding: FragmentNotificationBinding? = null
    private val binding get() = _binding!!
    private val authViewModel: AuthViewModel by inject()
    private val viewModel: NotificationViewModel by inject()

    private val token by lazy {
        authViewModel.getCredentialUser()?.token.toString()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotificationBinding.inflate(inflater, container, false)
        displayNotification()

        return binding.root
    }

    private fun displayNotification() {
        viewModel.showNotification(token).observe(viewLifecycleOwner) { resource ->
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

    private fun setupAdapter(data: NotificationResponse?) {
        val adapter = NotificationAdapter().apply {
            submitList(data?.data)
        }
        binding.recyclerViewNews.let {
            it.adapter = adapter
            it.layoutManager = LinearLayoutManager(requireContext())
        }

        if (data?.data.isNullOrEmpty()){
            binding.textResult.visibility = View.VISIBLE
        }
    }

}