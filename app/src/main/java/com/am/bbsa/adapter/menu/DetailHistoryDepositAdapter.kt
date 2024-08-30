package com.am.bbsa.adapter.menu

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.am.bbsa.data.response.DataItemDetailHistoryDeposit
import com.am.bbsa.databinding.ItemContentTypeWasteHistoryDepositBinding
import com.am.bbsa.utils.Formatter
import com.bumptech.glide.Glide

class DetailHistoryDepositAdapter :
    ListAdapter<DataItemDetailHistoryDeposit, DetailHistoryDepositAdapter.ViewHolder>(DIFF_CALLBACK) {
    inner class ViewHolder(private val binding: ItemContentTypeWasteHistoryDepositBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: DataItemDetailHistoryDeposit) {
            val total = data.sampah?.price?.times(data.sampah.weight)
            Glide.with(binding.root.context).load(data.sampah?.photo).into(binding.imageWaste)
            binding.textTitle.text = data.sampah?.name.toString()
            binding.textPrice.text = Formatter.formatCurrency(data.sampah?.price ?: 0)
            binding.textWeight.text = Formatter.formatKg(data.sampah?.weight ?: 0)
            binding.textTotal.text = Formatter.formatCurrency(total ?: 0)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemContentTypeWasteHistoryDepositBinding.inflate(
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
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataItemDetailHistoryDeposit>() {
            override fun areItemsTheSame(
                oldItem: DataItemDetailHistoryDeposit, newItem: DataItemDetailHistoryDeposit
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: DataItemDetailHistoryDeposit, newItem: DataItemDetailHistoryDeposit
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}