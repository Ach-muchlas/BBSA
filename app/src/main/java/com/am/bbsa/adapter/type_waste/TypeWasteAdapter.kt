package com.am.bbsa.adapter.type_waste

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.am.bbsa.data.response.DataItemSampah
import com.am.bbsa.databinding.ItemContentTypeWasteBinding
import com.am.bbsa.utils.Formatter
import com.bumptech.glide.Glide

class TypeWasteAdapter(private var hideButtonEditAndDelete: Boolean) :
    ListAdapter<DataItemSampah, TypeWasteAdapter.ViewHolder>(DIFF_CALLBACK) {
    var callbackOnclickEdit: ((DataItemSampah) -> Unit)? = null
    var callbackOnclickDelete: ((Int) -> Unit)? = null

    inner class ViewHolder(private val binding: ItemContentTypeWasteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(dataTypeWaste: DataItemSampah) {
            Glide.with(binding.root).load(dataTypeWaste.photo).into(binding.imageWaste)
            binding.textTypeWaste.text = dataTypeWaste.name
            binding.textPrice.text = Formatter.formatCurrency(dataTypeWaste.price ?: 0)
            binding.textCategory.text = dataTypeWaste.type
            binding.buttonEdit.setOnClickListener {
                callbackOnclickEdit?.invoke(dataTypeWaste)
            }
            binding.buttonDelete.setOnClickListener {
                callbackOnclickDelete?.invoke(dataTypeWaste.id)
            }
            if (hideButtonEditAndDelete) {
                binding.buttonEdit.visibility = View.GONE
                binding.buttonDelete.visibility = View.GONE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemContentTypeWasteBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataTypeWaste = getItem(position)
        holder.bind(dataTypeWaste)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataItemSampah>() {
            override fun areItemsTheSame(
                oldItem: DataItemSampah, newItem: DataItemSampah
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: DataItemSampah, newItem: DataItemSampah
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}