package com.am.bbsa.adapter.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.am.bbsa.R
import com.am.bbsa.data.response.DataItemHistoryDepositWaste
import com.am.bbsa.databinding.ItemContentHistoryDepositWasteBinding

class HistoryDepositWasteNasabahAdapter(
    private var onclick: ((Int) -> Unit)? = null
) :
    ListAdapter<DataItemHistoryDepositWaste, HistoryDepositWasteNasabahAdapter.ViewHolder>(
        DIFF_CALLBACK
    ) {

    fun setOnclickListener(listener: (Int) -> Unit) {
        onclick = listener
    }

    inner class ViewHolder(private val binding: ItemContentHistoryDepositWasteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: DataItemHistoryDepositWaste) {
            if (data.status == "Dikonfirmasi") {
                binding.textTitleStatus.text =
                    "Setoran telah ditimbang"
                binding.cardRoot.setOnClickListener {
                    onclick?.invoke(data.id ?: 0)
                }
                binding.iconStatus.setImageResource(R.drawable.icon_success)
            } else {
                binding.textTitleStatus.text =
                    "Setoran Sampah Sedang Diproses penimbangan"
            }
            binding.textDescStatus.text = "Detail dari data setoran dapat diklik"

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
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataItemHistoryDepositWaste>() {
            override fun areItemsTheSame(
                oldItem: DataItemHistoryDepositWaste, newItem: DataItemHistoryDepositWaste
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: DataItemHistoryDepositWaste, newItem: DataItemHistoryDepositWaste
            ): Boolean {
                return oldItem == newItem
            }
        }
    }


}