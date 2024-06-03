package com.am.bbsa.adapter.menu

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.am.bbsa.R
import com.am.bbsa.data.response.DataItemNasabah
import com.am.bbsa.databinding.ItemContentNasabahBinding
import com.bumptech.glide.Glide

class ListNasabahAdapter :
    ListAdapter<DataItemNasabah, ListNasabahAdapter.ViewHolder>(DIFF_CALLBACK) {
    var callbackOnclick: ((Int) -> Unit)? = null

    inner class ViewHolder(private val binding: ItemContentNasabahBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(dataNasabah: DataItemNasabah) {
            binding.root.setOnClickListener { callbackOnclick?.invoke(dataNasabah.id) }
            binding.imageProfile.setImageResource(R.drawable.icon_profile_man)
            binding.textName.text = dataNasabah.name
            binding.textNumberPhone.text = dataNasabah.phoneNumber
            binding.textStatusAccount.text = dataNasabah.id.toString()
            val icon = if (dataNasabah.gender.equals("Perempuan")) R.drawable.icon_profile_women else R.drawable.icon_profile_man
            Glide.with(binding.root.context).load(dataNasabah.photoProfile ?: icon).into(binding.imageProfile)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemContentNasabahBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataNasabah = getItem(position)
        holder.bind(dataNasabah)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataItemNasabah>() {
            override fun areItemsTheSame(
                oldItem: DataItemNasabah, newItem: DataItemNasabah
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: DataItemNasabah, newItem: DataItemNasabah
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}