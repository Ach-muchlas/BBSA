package com.am.bbsa.ui.admin.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.am.bbsa.R
import com.am.bbsa.databinding.FragmentMenuAdminBinding

class MenuAdminFragment : Fragment() {
    private var _binding: FragmentMenuAdminBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMenuAdminBinding.inflate(inflater, container, false)
        setupCardView()
        return binding.root
    }

    private fun setupCardView() {
        /*card nasabah*/
        binding.cardMenuAdmin.cardCustomers.apply {
            iconCard.setImageResource(R.drawable.icon_nasabah)
            textTitleCard.text = getString(R.string.nasabah)
        }

        /*card hasil penjualan*/
        binding.cardMenuAdmin.cardSalesResult.apply {
            iconCard.setImageResource(R.drawable.icon_penjualan)
            textTitleCard.text = getString(R.string.sales_result)
        }

        /*card setoran sampah nasabah*/
        binding.cardMenuAdmin.cardDepositCustomerWaste.apply {
            iconCard.setImageResource(R.drawable.icon_customer_deposit_waste)
            textTitleCard.text = getString(R.string.customer_deposit_waste)
        }

        /*card waste deposit*/
        binding.cardMenuAdmin.cardWasteDeposit.apply {
            iconCard.setImageResource(R.drawable.icon_waste_deposit)
            textTitleCard.text = getString(R.string.waste_deposit)
        }

        /*card history deposit*/
        binding.cardMenuAdmin.cardDepositHistory.apply {
            iconCard.setImageResource(R.drawable.icon_history)
            textTitleCard.text = getString(R.string.history_deposit)
        }

        /*card history transaction*/
        binding.cardMenuAdmin.cardBalanceWithdrawalHistory.apply {
            iconCard.setImageResource(R.drawable.icon_balance_withdrawal_history)
            textTitleCard.text = getString(R.string.balance_withdrawal_history)
        }

        /*card news*/
        binding.cardMenuAdmin.cardNews.apply {
            iconCard.setImageResource(R.drawable.icon_news)
            textTitleCard.text = getString(R.string.news)
        }

        /*card waste information*/
        binding.cardMenuAdmin.cardWasteInformation.apply {
            iconCard.setImageResource(R.drawable.icon_report)
            textTitleCard.text = getString(R.string.report_customer)
        }

        /*card pick up waste*/
        binding.cardMenuAdmin.cardSchedulingWastePickup.apply {
            iconCard.setImageResource(R.drawable.icon_pick_up_waste)
            textTitleCard.text = getString(R.string.pick_up_waste)
        }

        binding.cardMenuAdmin.cardSchedulingOpenWarehouse.apply {
            iconCard.setImageResource(R.drawable.icon_warehouse)
            textTitleCard.text = getString(R.string.schedulling_open_warehouse)
        }
    }
}