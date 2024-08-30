package com.am.bbsa.adapter.history_transaction

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.am.bbsa.data.response.DataItemHistoryTransaction
import com.am.bbsa.databinding.ItemContentTransactionHistoryBinding

class HistoryTransactionAdapter :
    ListAdapter<DataItemHistoryTransaction, HistoryTransactionAdapter.ViewHolder>(DIFF_CALLBACK) {
    inner class ViewHolder(private val binding: ItemContentTransactionHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: DataItemHistoryTransaction) {
            binding.textTitle.text = data.judul.toString()
            binding.textDesc.text = data.deskripsi.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemContentTransactionHistoryBinding.inflate(
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
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataItemHistoryTransaction>() {
            override fun areItemsTheSame(
                oldItem: DataItemHistoryTransaction, newItem: DataItemHistoryTransaction
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: DataItemHistoryTransaction, newItem: DataItemHistoryTransaction
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}