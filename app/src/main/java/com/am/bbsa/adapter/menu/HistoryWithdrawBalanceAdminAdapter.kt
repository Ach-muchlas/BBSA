package com.am.bbsa.adapter.menu

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.am.bbsa.R
import com.am.bbsa.data.response.DataItemHistoryWithdrawBalance
import com.am.bbsa.databinding.ItemContentHistoryWithdrawalAdminBinding
import com.am.bbsa.utils.Formatter

class HistoryWithdrawBalanceAdminAdapter(private var onclick: ((Int) -> Unit)? = null) :
    ListAdapter<DataItemHistoryWithdrawBalance, HistoryWithdrawBalanceAdminAdapter.ViewHolder>(
        DIFF_CALLBACK
    ) {

    fun setOnclickListener(listener: (Int) -> Unit) {
        onclick = listener
    }
    inner class ViewHolder(private val binding: ItemContentHistoryWithdrawalAdminBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: DataItemHistoryWithdrawBalance) {
            binding.cardRoot.setOnClickListener {
                onclick?.invoke(data.id)
            }

            binding.textTitle.text = data.username.toString()
            when (data.status) {
                "COMPLETED" -> {
                    binding.iconChecked.setImageResource(R.drawable.icon_success)
                }

                "PENDING" -> {
                    binding.iconChecked.setImageResource(R.drawable.icon_waiting)
                }

                else -> {
                    binding.iconChecked.setImageResource(R.drawable.icon_reject)
                }
            }
            binding.textTarget.text = buildString {
                append("No Rekening ")
                append(data.nomorRekening)
            }

            binding.textMethod.text = buildString {
                append("Metode yang digunakan ")
                append(data.metodePenarikan)
            }
            binding.textDate.text = Formatter.formatDate(data.createdAt.toString())
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemContentHistoryWithdrawalAdminBinding.inflate(
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
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataItemHistoryWithdrawBalance>() {
            override fun areItemsTheSame(
                oldItem: DataItemHistoryWithdrawBalance,
                newItem: DataItemHistoryWithdrawBalance
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: DataItemHistoryWithdrawBalance,
                newItem: DataItemHistoryWithdrawBalance
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}