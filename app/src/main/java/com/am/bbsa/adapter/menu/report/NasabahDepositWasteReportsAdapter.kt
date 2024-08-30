package com.am.bbsa.adapter.menu.report

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.am.bbsa.data.response.report.DataItemNasabahWasteDepositReport
import com.am.bbsa.data.response.report.SampahItem
import com.am.bbsa.databinding.ItemContentReportNasabahDepositWasteBinding
import com.am.bbsa.utils.Formatter

class NasabahDepositWasteReportsAdapter(private val dataSet: List<DataItemNasabahWasteDepositReport>) :
    RecyclerView.Adapter<NasabahDepositWasteReportsAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: ItemContentReportNasabahDepositWasteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: DataItemNasabahWasteDepositReport, showDate: Boolean) {
            if (showDate) {
                binding.textDate.text = Formatter.formatDate2(data.tanggalSetor.toString())
                binding.textDate.visibility = View.VISIBLE
            } else {
                binding.textDate.visibility = View.GONE
            }

            val wasteListAdapter = WasteDetailReportAdapter(data.sampah!! as List<SampahItem>)
            binding.recyclerViewTypeWasteAndWeight.layoutManager =
                LinearLayoutManager(binding.root.context)
            binding.recyclerViewTypeWasteAndWeight.adapter = wasteListAdapter
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemContentReportNasabahDepositWasteBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    override fun getItemCount(): Int = dataSet.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dataSet[position]
        val showDate =
            position == 0 || Formatter.formatDate2(data.tanggalSetor.toString()) != Formatter.formatDate2(
                dataSet[position - 1].tanggalSetor.toString()
            )
        holder.bind(data, showDate)

    }
}