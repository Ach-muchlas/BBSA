package com.am.bbsa.adapter.notification

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.am.bbsa.data.response.DataItemNotification
import com.am.bbsa.databinding.ItemContentNotificationBinding

class NotificationAdapter :
    ListAdapter<DataItemNotification, NotificationAdapter.ViewHolder>(DIFF_CALLBACK) {
    inner class ViewHolder(private val binding: ItemContentNotificationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: DataItemNotification) {
            binding.textViewTitle.text = data.judul
            binding.textViewDescription.text = data.pesan
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemContentNotificationBinding.inflate(
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
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataItemNotification>() {
            override fun areItemsTheSame(
                oldItem: DataItemNotification, newItem: DataItemNotification
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: DataItemNotification, newItem: DataItemNotification
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}