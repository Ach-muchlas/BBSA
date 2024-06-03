package com.am.bbsa.adapter.schedule_pick_up

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.am.bbsa.data.response.DataItemNasabahRegistrantPickupWaste
import com.am.bbsa.databinding.ItemContentNasabahRegistrantPickUpWasteBinding
import com.am.bbsa.utils.Formatter
import com.bumptech.glide.Glide

class NasabahRegistrantPickupWasteAdapter(
    private var hideButtonApproveAndReject: Boolean,
    private var callbackOnclickToDetail: ((Int) -> Unit)? = null,
    private var callbackOnclickApprove: ((Int) -> Unit)? = null,
    private var callbackOnclickReject: ((Int) -> Unit)? = null
) :
    ListAdapter<DataItemNasabahRegistrantPickupWaste, NasabahRegistrantPickupWasteAdapter.ViewHolder>(
        DIFF_CALLBACK
    ) {
    fun setOnclickListener(listener: (Int) -> Unit) {
        callbackOnclickToDetail = listener
    }

    fun setOnApprovedListener(listener: (Int) -> Unit){callbackOnclickApprove = listener}
    fun setOnRejectListener(listener: (Int) -> Unit){callbackOnclickReject = listener}

    inner class ViewHolder(private val binding: ItemContentNasabahRegistrantPickUpWasteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: DataItemNasabahRegistrantPickupWaste) {
            Glide.with(binding.root.context).load(data.photoProfile).into(binding.imagePhotoProfile)
            binding.textName.text = data.userName
            binding.textDate.text = Formatter.formatDate2(data.tanggal.toString())
            binding.cardRoot.setOnClickListener {
                callbackOnclickToDetail?.invoke(data.id)
            }
            binding.buttonApprove.setOnClickListener {
                callbackOnclickApprove?.invoke(data.id)
            }
            binding.buttonReject.setOnClickListener {
                callbackOnclickReject?.invoke(data.id)
            }

            if (hideButtonApproveAndReject) {
                binding.buttonApprove.visibility = View.GONE
                binding.buttonReject.visibility = View.GONE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemContentNasabahRegistrantPickUpWasteBinding.inflate(
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
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataItemNasabahRegistrantPickupWaste>() {
            override fun areItemsTheSame(
                oldItem: DataItemNasabahRegistrantPickupWaste,
                newItem: DataItemNasabahRegistrantPickupWaste
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: DataItemNasabahRegistrantPickupWaste,
                newItem: DataItemNasabahRegistrantPickupWaste
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}