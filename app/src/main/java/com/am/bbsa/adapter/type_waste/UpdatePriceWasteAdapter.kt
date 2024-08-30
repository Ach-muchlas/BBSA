package com.am.bbsa.adapter.type_waste

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.am.bbsa.data.response.DataItemSampah
import com.am.bbsa.databinding.ItemContentUpdatePriceWasteBinding
import com.am.bbsa.utils.Formatter

class UpdatePriceWasteAdapter :
    ListAdapter<DataItemSampah, UpdatePriceWasteAdapter.ViewHolder>(
        DIFF_CALLBACK
    ) {

    inner class ViewHolder(private val binding: ItemContentUpdatePriceWasteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: DataItemSampah) {
            binding.textTypeWaste.text = data.name.toString()
            binding.edtTextPrice.setText(Formatter.formatCurrency(data.price ?: 0))

            binding.edtTextPrice.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    val price = p0.toString().replace(Regex("\\D"), "").toIntOrNull()
                    data.price = price ?: data.price
                }

                override fun afterTextChanged(p0: Editable?) {}
            })
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemContentUpdatePriceWasteBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = getItem(position)
        holder.bind(data)
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