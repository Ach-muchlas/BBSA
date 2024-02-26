package com.am.bbsa.ui.customers.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.am.bbsa.R
import com.am.bbsa.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        setupViewMenu()
        return binding.root
    }

    private fun setupViewMenu() {
        /*menu withdraw*/
        binding.menuWithdrawBalance.apply {
            iconMenu.setImageResource(R.drawable.icon_widrawal_balance)
            textTitleMenu.text = getString(R.string.withdraw_balance)
        }

        /*menu pickup*/
        binding.menuPickUpWaste.apply {
            iconMenu.setImageResource(R.drawable.icon_trash_pickup)
            textTitleMenu.text = getString(R.string.pick_up_waste)
        }

        binding.menuHistoryDeposit.apply {
            iconMenu.setImageResource(R.drawable.icon_waste_deposit_history)
            textTitleMenu.text = getString(R.string.history_deposit)
        }

        binding.menuTypeWaste.apply {
            iconMenu.setImageResource(R.drawable.icon_type_waste)
            textTitleMenu.text = getString(R.string.type_waste)
        }
    }
}