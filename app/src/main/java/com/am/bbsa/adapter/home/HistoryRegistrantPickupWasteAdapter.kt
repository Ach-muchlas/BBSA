package com.am.bbsa.adapter.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.am.bbsa.R
import com.am.bbsa.data.response.DataItemHistoryRegistrantPickupWaste
import com.am.bbsa.databinding.ItemContentHistoryDepositWasteBinding
import com.am.bbsa.utils.Formatter

class HistoryRegistrantPickupWasteAdapter :
    ListAdapter<DataItemHistoryRegistrantPickupWaste, HistoryRegistrantPickupWasteAdapter.ViewHolder>(
        DIFF_CALLBACK
    ) {
    inner class ViewHolder(private val binding: ItemContentHistoryDepositWasteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: DataItemHistoryRegistrantPickupWaste) {
            when (data.status) {
                "Diterima" -> {
                    binding.iconStatus.setImageResource(R.drawable.icon_success)
                    binding.textTitleStatus.text = "Pendaftaran anda diterima"
                    binding.textDescStatus.text = Formatter.formatDate2(data.createdAt.toString())
                }
                "Ditolak" -> {
                    binding.iconStatus.setImageResource(R.drawable.icon_reject)
                    binding.textTitleStatus.text = "Pendaftaran anda ditolak"
                    binding.textDescStatus.text = Formatter.formatDate2(data.createdAt.toString())
                }
                else -> {
                    binding.iconStatus.setImageResource(R.drawable.icon_waiting)
                    binding.textTitleStatus.text = "Menunggu..."
                    binding.textDescStatus.text = Formatter.formatDate2(data.createdAt.toString())
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemContentHistoryDepositWasteBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = getItem(position)
        holder.bind(data)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataItemHistoryRegistrantPickupWaste>() {
            override fun areItemsTheSame(
                oldItem: DataItemHistoryRegistrantPickupWaste,
                newItem: DataItemHistoryRegistrantPickupWaste
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: DataItemHistoryRegistrantPickupWaste,
                newItem: DataItemHistoryRegistrantPickupWaste
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}