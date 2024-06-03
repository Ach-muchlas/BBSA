package com.am.bbsa.adapter.menu

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.am.bbsa.R
import com.am.bbsa.data.response.DataItemNasabah
import com.am.bbsa.databinding.ItemContentReportNasabahBinding
import com.bumptech.glide.Glide

class ReportNasabahAdapter(private var onClickDownload: ((Int) -> Unit)? = null) :
    ListAdapter<DataItemNasabah, ReportNasabahAdapter.ViewHolder>(
        DIFF_CALLBACK
    ) {

    fun onDownloadClickListener(listener: (Int) -> Unit) {
        onClickDownload = listener
    }

    inner class ViewHolder(private val binding: ItemContentReportNasabahBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: DataItemNasabah) {
            binding.buttonDownload.setOnClickListener {
                onClickDownload?.invoke(data.userId)
            }
            if (data.photoProfile.isNullOrEmpty()) {
                if (data.gender == "Perempuan") {
                    binding.imageProfile.setImageResource(R.drawable.icon_profile_women)
                } else {
                    binding.imageProfile.setImageResource(R.drawable.icon_profile_man)
                }
            } else {
                Glide.with(binding.root.context).load(data.photoProfile)
                    .into(binding.imageProfile)
            }
            binding.textName.text = data.name.toString()
            binding.textNumberPhone.text = data.phoneNumber
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemContentReportNasabahBinding.inflate(
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