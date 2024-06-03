package com.am.bbsa.adapter.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.am.bbsa.R
import com.am.bbsa.data.dummy_model.DummyModel

class SelectWithdrawBalanceMethodAdapter(
    context: Context,
    resource: Int,
    private val bankList: List<DummyModel.DataBank>
) : ArrayAdapter<DummyModel.DataBank>(context, resource, bankList) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.dropdown_item, parent, false)
        val bank = bankList[position]
        val textView = view.findViewById<TextView>(R.id.textViewDropdown)
        textView.text = bank.nameBank
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getView(position, convertView, parent)
    }

    override fun getItem(position: Int): DummyModel.DataBank {
        return bankList[position]
    }

    override fun getItemId(position: Int): Long {
        return bankList[position].id.toLong()
    }
}