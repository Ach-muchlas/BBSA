package com.am.bbsa.adapter.account

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.am.bbsa.data.dummy_model.DummyModel
import com.am.bbsa.databinding.ItemContentMenuCardBinding

class AccountCardAdapter :
    ListAdapter<DummyModel.DataCard, AccountCardAdapter.ViewHolder>(DIFF_CALLBACK) {
    var callbackOnclick: ((Int) -> Unit)? = null

    inner class ViewHolder(private val binding: ItemContentMenuCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(dataCard: DummyModel.DataCard) {
            binding.textTitleCard.text = binding.root.context.getString(dataCard.title)
            binding.iconCard.setImageResource(dataCard.icon)
            binding.root.setOnClickListener {
                callbackOnclick?.invoke(dataCard.id)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemContentMenuCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataCard = getItem(position)
        holder.bind(dataCard)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DummyModel.DataCard>() {
            override fun areItemsTheSame(
                oldItem: DummyModel.DataCard, newItem: DummyModel.DataCard
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: DummyModel.DataCard, newItem: DummyModel.DataCard
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}