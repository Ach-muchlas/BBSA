package com.am.bbsa.adapter.menu

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.am.bbsa.data.response.DataItemHistoryDeposit
import com.am.bbsa.databinding.ItemContentHistoryDepositAdminBinding
import com.am.bbsa.utils.Formatter

class HistoryDepositAdapter(
    private var onclick: ((Int) -> Unit)? = null
) :
    ListAdapter<DataItemHistoryDeposit, HistoryDepositAdapter.ViewHolder>(DIFF_CALLBACK) {

    fun setOnclickListener(listener: (Int) -> Unit) {
        onclick = listener
    }

    inner class ViewHolder(private val binding: ItemContentHistoryDepositAdminBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(dataHistory: DataItemHistoryDeposit) {
            binding.textName.text = dataHistory.user?.name
            binding.textDate.text = Formatter.formatDate(dataHistory.createdAt.toString())
            binding.textTotal.text = Formatter.formatCurrency(dataHistory.totalSetoran ?: 0)
            binding.cardRoot.setOnClickListener {
                onclick?.invoke(dataHistory.id ?: 0)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemContentHistoryDepositAdminBinding.inflate(
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
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataItemHistoryDeposit>() {
            override fun areItemsTheSame(
                oldItem: DataItemHistoryDeposit, newItem: DataItemHistoryDeposit
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: DataItemHistoryDeposit, newItem: DataItemHistoryDeposit
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}