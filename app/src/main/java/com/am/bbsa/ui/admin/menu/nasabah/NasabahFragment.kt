package com.am.bbsa.ui.admin.menu.nasabah

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.am.bbsa.adapter.menu.ListNasabahAdapter
import com.am.bbsa.data.response.NasabahResponse
import com.am.bbsa.databinding.FragmentNasabahBinding
import com.am.bbsa.service.source.Status
import com.am.bbsa.ui.admin.menu.MenuViewModel
import com.am.bbsa.ui.auth.AuthViewModel
import com.am.bbsa.utils.Destination
import com.am.bbsa.utils.Navigation
import com.am.bbsa.utils.UiHandler
import org.koin.android.ext.android.inject

class NasabahFragment : Fragment() {
    private var _binding: FragmentNasabahBinding? = null
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
        _binding = FragmentNasabahBinding.inflate(inflater, container, false)
        UiHandler.setupVisibilityBottomNavigationAdmin(activity, true)
        displayNasabah()
        setupNavigation()
        return binding.root
    }

    private fun setupNavigation() {
        binding.buttonBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun displayNasabah() {
        viewModel.showAllNasabah(token).observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                Status.LOADING -> {}
                Status.SUCCESS -> {
                    setupAdapter(resource.data!!)
                }

                Status.ERROR -> {
                    UiHandler.toastErrorMessage(requireContext(), resource.message.toString())
                }
            }
        }
    }

    private fun setupAdapter(data: NasabahResponse) {
        val adapter = ListNasabahAdapter().apply {
            submitList(data.data)
            callbackOnclick = { nasabah_id ->
                /*go to detail nasabah page*/
                Navigation.navigationFragment(
                    Destination.NASABAH_TO_DETAIL_NASABAH,
                    findNavController(),
                    Bundle().apply {
                        putInt(KEY_NASABAH_ID, nasabah_id)
                    }
                )
            }
        }
        binding.recyclerViewNasabah.let {
            it.adapter = adapter
            it.layoutManager = LinearLayoutManager(requireContext())
        }
    }

    companion object {
        const val KEY_NASABAH_ID = "nasabah_id"
    }

    override fun onDestroy() {
        super.onDestroy()
        UiHandler.setupVisibilityBottomNavigationAdmin(activity, false)
    }
}