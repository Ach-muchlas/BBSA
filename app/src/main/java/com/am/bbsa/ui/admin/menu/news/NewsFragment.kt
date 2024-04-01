package com.am.bbsa.ui.admin.menu.news

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
import com.am.bbsa.databinding.FragmentNewsBinding
import com.am.bbsa.service.source.Status
import com.am.bbsa.ui.admin.menu.MenuViewModel
import com.am.bbsa.ui.auth.AuthViewModel
import com.am.bbsa.utils.Destination
import com.am.bbsa.utils.Navigation
import com.am.bbsa.utils.UiHandler
import org.koin.android.ext.android.inject

class NewsFragment : Fragment() {
    private var _binding: FragmentNewsBinding? = null
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
        _binding = FragmentNewsBinding.inflate(inflater, container, false)
        setupNavigation()
        displayNews()
        setupView()
        return binding.root
    }

    private fun setupView() {
        UiHandler.setupVisibilityBottomNavigationAdmin(activity, true)
        binding.viewAppbar.textTitleAppBar.setText(R.string.news)
    }

    private fun setupNavigation() {
        binding.viewAppbar.buttonBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.buttonAdd.setOnClickListener {
            Navigation.navigationFragment(Destination.NEWS_TO_ADD_NEWS, findNavController())
        }
    }

    private fun displayNews() {
        viewModel.showAllNews(token).observe(viewLifecycleOwner) { resource ->
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

    private fun setupAdapter(data: NewsResponse?) {
        val adapter = NewsAdapter().apply {
            submitList(data?.data)
            callbackOnclick = { data ->
                val bundle = Bundle().apply {
                    putParcelable(KEY_DATA_NEWS, data)
                }

                Navigation.navigationFragment(
                    Destination.NEWS_TO_DETAIL_NEWS,
                    findNavController(),
                    bundle
                )
            }
        }

        binding.recyclerViewNews.let {
            it.adapter = adapter
            it.layoutManager = LinearLayoutManager(requireContext())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        UiHandler.setupVisibilityBottomNavigationAdmin(activity, false)
    }

    companion object {
        const val KEY_DATA_NEWS = "key_data_sampah"
    }

}