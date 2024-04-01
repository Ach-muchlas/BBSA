package com.am.bbsa.adapter.menu

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.am.bbsa.R
import com.am.bbsa.data.response.DataItemDepositWeighing
import com.am.bbsa.data.response.DataItemWasteDeposit
import com.am.bbsa.databinding.ItemContentDepositWeighingBinding
import com.am.bbsa.utils.Formatter

class DepositWeighingAdapter :
    ListAdapter<DataItemDepositWeighing, DepositWeighingAdapter.ViewHolder>(DIFF_CALLBACK) {
    var callbackOnclick: ((DataItemDepositWeighing) -> Unit)? = null

    inner class ViewHolder(private val binding: ItemContentDepositWeighingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: DataItemDepositWeighing) {
            binding.textName.text = data.username
            binding.imageProfile.setImageResource(R.drawable.icon_profile_man)
            binding.textDate.text = Formatter.formatDate(data.createdAt ?: "")
            binding.root.setOnClickListener {
                callbackOnclick?.invoke(data)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DepositWeighingAdapter.ViewHolder {
        return ViewHolder(
            ItemContentDepositWeighingBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: DepositWeighingAdapter.ViewHolder, position: Int) {
        val dataDeposit = getItem(position)
        holder.bind(dataDeposit)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataItemDepositWeighing>() {
            override fun areItemsTheSame(
                oldItem: DataItemDepositWeighing, newItem: DataItemDepositWeighing
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: DataItemDepositWeighing, newItem: DataItemDepositWeighing
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}