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
    ArrayAdapter<DataItemNasabah>(context, resource, nasabahList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.dropdown_item, parent, false)
        val nasabah = nasabahList[position]
        val textView = view.findViewById<TextView>(R.id.textViewDropdown)
        textView.text = buildString {
        append(nasabah.id)
        append(". ")
        append(nasabah.name)
    }
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getView(position, convertView, parent)
    }

    override fun getItem(position: Int): DataItemNasabah {
        return nasabahList[position]
    }

    override fun getItemId(position: Int): Long {
        return nasabahList[position].id.toLong()
    }
}