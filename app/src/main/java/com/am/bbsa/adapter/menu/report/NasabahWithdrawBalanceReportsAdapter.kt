package com.am.bbsa.adapter.menu.report

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.am.bbsa.data.response.report.DataItemNasabahWithdrawBalanceReports
import com.am.bbsa.databinding.ItemContentReportNasabahWithdrawBalanceBinding
import com.am.bbsa.utils.Formatter

class NasabahWithdrawBalanceReportsAdapter :
    ListAdapter<DataItemNasabahWithdrawBalanceReports, NasabahWithdrawBalanceReportsAdapter.ViewHolder>(
        DIFF_CALLBACK
    ) {
    inner class ViewHolder(private val binding: ItemContentReportNasabahWithdrawBalanceBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: DataItemNasabahWithdrawBalanceReports) {
            binding.textDate.text = Formatter.formatDate2(data.tanggalPenarikanSaldo.toString())
            binding.textMethod.text = data.metodePenarikan.toString()
            binding.textAccountName.text = data.namaPemilikAkun.toString()
            binding.textAccountNumber.text = data.nomorRekening.toString()
            binding.textTotal.text = Formatter.formatCurrency(data.totalPenarikan ?: 0)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemContentReportNasabahWithdrawBalanceBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = getItem(position)
        holder.bind(data)
    }

    companion object {
        val DIFF_CALLBACK =
            object : DiffUtil.ItemCallback<DataItemNasabahWithdrawBalanceReports>() {
                override fun areItemsTheSame(
                    oldItem: DataItemNasabahWithdrawBalanceReports,
                    newItem: DataItemNasabahWithdrawBalanceReports
                ): Boolean {
                    return oldItem == newItem
                }

                override fun areContentsTheSame(
                    oldItem: DataItemNasabahWithdrawBalanceReports,
                    newItem: DataItemNasabahWithdrawBalanceReports
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }
}