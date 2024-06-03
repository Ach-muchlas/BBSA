package com.am.bbsa.ui.admin.menu.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.am.bbsa.R
import com.am.bbsa.data.response.DataItemNews
import com.am.bbsa.data.response.news.DetailNewsResponse
import com.am.bbsa.databinding.FragmentDetailNewsBinding
import com.am.bbsa.service.source.Status
import com.am.bbsa.ui.admin.menu.MenuViewModel
import com.am.bbsa.ui.auth.AuthViewModel
import com.am.bbsa.utils.Destination
import com.am.bbsa.utils.KEY
import com.am.bbsa.utils.Navigation
import com.am.bbsa.utils.UiHandler
import com.bumptech.glide.Glide
import org.koin.android.ext.android.inject

class DetailNewsFragment : Fragment() {
    private var _binding: FragmentDetailNewsBinding? = null
    private val binding get() = _binding!!
    private val authViewModel: AuthViewModel by inject()
    private val menuViewModel: MenuViewModel by inject()
    private val token: String by lazy { authViewModel.getCredentialUser()?.token.toString() }
    private val receiveBundle by lazy {
        arguments?.getParcelable<DataItemNews>(NewsFragment.KEY_DATA_NEWS)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailNewsBinding.inflate(inflater, container, false)
        initialize()
        setupOnNewsUpdated()
        setupNavigation()
        return binding.root
    }

    private fun initialize() {
        UiHandler.setupVisibilityBottomNavigationAdmin(activity, true)
    }

    private fun setupView(data: DetailNewsResponse?) {
        Glide.with(requireContext()).load(data?.data?.foto).into(binding.imageViewNews)
        binding.textTitleNews.text = data?.data?.judul.toString()
        binding.textContentNews.text = data?.data?.deskripsi.toString()
    }

    private fun setupOnNewsUpdated() {
        menuViewModel.showDetailNews(token, receiveBundle?.id ?: 0).observe(viewLifecycleOwner) {
            when (it.status) {
                Status.LOADING -> {}
                Status.SUCCESS -> {
                    setupView(it.data)
                }

                Status.ERROR -> {
                    UiHandler.toastErrorMessage(requireContext(), it.message.toString())
                }
            }
        }
    }

    private fun setupDeleteNews() {
        val id: Int = receiveBundle?.id ?: 0
        menuViewModel.deleteNews(token, id).observe(viewLifecycleOwner) {
            when (it.status) {
                Status.LOADING -> {
                    UiHandler.toastSuccessMessage(requireContext(), "Mohon Menunggu")
                }

                Status.SUCCESS -> {
                    UiHandler.toastSuccessMessage(requireContext(), "Berhasil Menghapus berita")
                    findNavController().popBackStack()
                }

                Status.ERROR -> {
                    UiHandler.toastErrorMessage(requireContext(), it.message.toString())
                }
            }
        }
    }

    private fun setupNavigation() {
        binding.topAppBar.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.edit_news -> {
                    val bundle = Bundle().apply {
                        putParcelable(KEY.BUNDLE_PARCELABLE_NEWS, receiveBundle)
                    }

                    Navigation.navigationFragment(
                        Destination.DETAIL_NEWS_TO_UPDATE_NEWS,
                        findNavController(),
                        bundle
                    )
                    true
                }

                R.id.delete_news -> {
                    setupDeleteNews()
                    true
                }

                else -> false
            }
        }
    }
}