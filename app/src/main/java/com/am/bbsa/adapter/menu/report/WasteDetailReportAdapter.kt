package com.am.bbsa.adapter.menu.report

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.am.bbsa.data.response.report.SampahItem
import com.am.bbsa.databinding.ItemContentDetailReportDepositWasteBinding

class WasteDetailReportAdapter(private val wasteList: List<SampahItem>) :
    RecyclerView.Adapter<WasteDetailReportAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: ItemContentDetailReportDepositWasteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: SampahItem) {
            binding.textType.text = data.nama.toString()
            binding.textWeight.text = data.beratSampah.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemContentDetailReportDepositWasteBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    override fun getItemCount(): Int = wasteList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(wasteList[position])
    }

}