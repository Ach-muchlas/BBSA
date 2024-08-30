package com.am.bbsa.ui.admin.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.am.bbsa.adapter.menu.MenuCardAdapter
import com.am.bbsa.data.dummy_data.DummyData
import com.am.bbsa.databinding.FragmentMenuAdminBinding
import com.am.bbsa.utils.Destination
import com.am.bbsa.utils.Navigation

class MenuAdminFragment : Fragment() {
    private var _binding: FragmentMenuAdminBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMenuAdminBinding.inflate(inflater, container, false)
        setupAdapter()
        return binding.root
    }

    private fun setupAdapter() {
        val adapter = MenuCardAdapter()
        adapter.submitList(DummyData.DummyDataCardMenu)
        adapter.callbackOnclick = { id ->
            when (id) {
                1 -> Navigation.navigationFragment(Destination.MENU_TO_NASABAH, findNavController())
                2 -> Navigation.navigationFragment(
                    Destination.MENU_TO_UPDATE_PRICE,
                    findNavController()
                )

                3 -> Navigation.navigationFragment(
                    Destination.MENU_TO_WASTE_DEPOSIT_ADMIN,
                    findNavController()
                )
                4 -> Navigation.navigationFragment(Destination.MENU_TO_DEPOSIT_WEIGHING, findNavController())
                5 -> Navigation.navigationFragment(
                    Destination.MENU_TO_HISTORY_DEPOSIT,
                    findNavController()
                )

                6 -> Navigation.navigationFragment(
                    Destination.MENU_TO_HISTORY_WITHDRAW_BALANCE,
                    findNavController()
                )

                7 -> Navigation.navigationFragment(Destination.MENU_TO_NEWS, findNavController())
                8 -> Navigation.navigationFragment(
                    Destination.MENU_TO_WASTE_TYPE_INFORMATION, findNavController()
                )
                9 -> Navigation.navigationFragment(Destination.MENU_TO_REPORT_FRAGMENT, findNavController())
                10 -> Navigation.navigationFragment(
                    Destination.MENU_TO_SCHEDULING_PICK_UP,
                    findNavController()
                )
            }
        }
        binding.cardMenuAdmin.recyclerViewCardAccount.let {
            it.adapter = adapter
            it.layoutManager = LinearLayoutManager(requireContext())
        }
    }
}