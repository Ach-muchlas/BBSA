package com.am.bbsa.adapter.menu

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.am.bbsa.data.dummy_model.DummyModel
import com.am.bbsa.databinding.ItemContentMenuCardBinding

class MenuCardAdapter : ListAdapter<DummyModel.DataCard, MenuCardAdapter.ViewHolder>(DIFF_CALLBACK) {
    var callbackOnclick : ((Int) -> Unit)? = null

    inner class ViewHolder(private val binding: ItemContentMenuCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
            fun bind(dataMenu : DummyModel.DataCard){
                binding.iconCard.setImageResource(dataMenu.icon)
                binding.textTitleCard.text = binding.root.context.getString(dataMenu.title)
                binding.cardRootMenu.setOnClickListener {
                    callbackOnclick?.invoke(dataMenu.id)
                }
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemContentMenuCardBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataMenu = getItem(position)
        holder.bind(dataMenu)
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