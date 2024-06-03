package com.am.bbsa.ui.customers.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.am.bbsa.data.response.DataItemNews
import com.am.bbsa.databinding.FragmentDetailNewsNasabahBinding
import com.am.bbsa.utils.UiHandler
import com.bumptech.glide.Glide

class DetailNewsNasabahFragment : Fragment() {
    private var _binding: FragmentDetailNewsNasabahBinding? = null
    private val binding get() = _binding!!
    private val receiveBundle by lazy {
        arguments?.getParcelable<DataItemNews>(HomeFragment.KEY_DATA_NEWS)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailNewsNasabahBinding.inflate(inflater, container, false)
        setupView()
        setupNavigation()
        return binding.root
    }

    private fun setupView() {
        UiHandler.setupVisibilityBottomNavigationNasabah(activity, true)
        Glide.with(requireContext()).load(receiveBundle?.photo).into(binding.imageViewNews)
        binding.textTitleNews.text = receiveBundle?.title
        binding.textContentNews.text = receiveBundle?.description
    }

    private fun setupNavigation() {
        binding.cardButtonBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        UiHandler.setupVisibilityBottomNavigationNasabah(activity, false)
    }
}