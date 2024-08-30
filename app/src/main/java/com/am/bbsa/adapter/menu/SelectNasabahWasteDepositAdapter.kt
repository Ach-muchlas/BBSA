package com.am.bbsa.adapter.menu

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.am.bbsa.R
import com.am.bbsa.data.response.DataItemNasabah

class SelectNasabahWasteDepositAdapter(context: Context, resource: Int, private val nasabahList: List<DataItemNasabah>) :
    ArrayAdapter<DataItemNasabah>(context, resource,nasabahList.filter { it.status == "Aktif" }) {

    private val filteredNasabahList: List<DataItemNasabah> = nasabahList.filter { it.status == "Aktif" }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.dropdown_item, parent, false)
        val nasabah = filteredNasabahList[position]
        val textView = view.findViewById<TextView>(R.id.textViewDropdown)
        textView.text = buildString {
        append(nasabah.userId)
        append(". ")
        append(nasabah.name)
    }
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getView(position, convertView, parent)
    }

    override fun getItem(position: Int): DataItemNasabah {
        return filteredNasabahList[position]
    }

    override fun getItemId(position: Int): Long {
        return filteredNasabahList[position].id.toLong()
    }
}