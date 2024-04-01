package com.am.bbsa.ui.admin.menu.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.am.bbsa.R
import com.am.bbsa.data.response.DataItemNews
import com.am.bbsa.databinding.FragmentDetailNewsBinding
import com.am.bbsa.utils.Destination
import com.am.bbsa.utils.Navigation
import com.am.bbsa.utils.UiHandler
import com.bumptech.glide.Glide

class DetailNewsFragment : Fragment() {
    private var _binding: FragmentDetailNewsBinding? = null
    private val binding get() = _binding!!
    private val receiveBundle by lazy {
        arguments?.getParcelable<DataItemNews>(NewsFragment.KEY_DATA_NEWS)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailNewsBinding.inflate(inflater, container, false)
        setupView()
        setupNavigation()
        return binding.root
    }

    private fun setupView() {
        Glide.with(requireContext()).load(receiveBundle?.photo).into(binding.imageViewNews)
        binding.textTitleNews.text = receiveBundle?.title
        binding.textContentNews.text = receiveBundle?.description
    }

    private fun setupNavigation() {
        binding.topAppBar.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.edit_news -> {
                    Navigation.navigationFragment(
                        Destination.DETAIL_NEWS_TO_UPDATE_NEWS,
                        findNavController()
                    )
                    true
                }

                R.id.delete_news -> {
                    UiHandler.toastSuccessMessage(requireContext(), "Success delete news")
                    true
                }

                else -> false
            }
        }
//        binding.buttonBack.setOnClickListener {
//            findNavController().popBackStack()
//        }
//
//        binding.buttonEditNews.setOnClickListener {
//            Navigation.navigationFragment(
//                Destination.DETAIL_NEWS_TO_UPDATE_NEWS,
//                findNavController()
//            )
//        }
//
//        binding.buttonDeleteNews.setOnClickListener {
//            /*delete news*/
//        }
    }
}