package com.am.bbsa.adapter.balance

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.am.bbsa.R
import com.am.bbsa.data.response.DataItemWithdrawBalance
import com.am.bbsa.databinding.ItemContentHistoryWithdrawalBinding
import com.am.bbsa.utils.Formatter

class HistoryWithdrawBalanceAdapter(private var onclick: ((Int) -> Unit)? = null) :
    ListAdapter<DataItemWithdrawBalance, HistoryWithdrawBalanceAdapter.ViewHolder>(
        DIFF_CALLBACK
    ) {

    fun setOnClickListener(listener: (Int) -> Unit) {
        onclick = listener
    }

    inner class ViewHolder(private val binding: ItemContentHistoryWithdrawalBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: DataItemWithdrawBalance) {
            binding.cardRoot.setOnClickListener {
                onclick?.invoke(data.id)
            }
            when (data.status) {
                "COMPLETED" -> {
                    binding.textTitle.text = "Saldo Berhasil Ditarik"
                    binding.iconChecked.setImageResource(R.drawable.icon_success)
                }

                "PENDING" -> {
                    binding.textTitle.text = "Menunggu..."
                    binding.iconChecked.setImageResource(R.drawable.icon_waiting)
                }

                else -> {
                    binding.textTitle.text = "Penarikan Saldo Gagal"
                    binding.iconChecked.setImageResource(R.drawable.icon_reject)
                }
            }
            binding.textTarget.text = buildString {
                append("No Rekening ")
                append(data.metodePenarikan)
            }

            binding.textDate.text = Formatter.formatDate(data.createdAt.toString())
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemContentHistoryWithdrawalBinding.inflate(
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
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataItemWithdrawBalance>() {
            override fun areItemsTheSame(
                oldItem: DataItemWithdrawBalance, newItem: DataItemWithdrawBalance
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: DataItemWithdrawBalance, newItem: DataItemWithdrawBalance
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}