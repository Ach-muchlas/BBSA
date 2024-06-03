package com.am.bbsa.ui.bottom_sheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.am.bbsa.databinding.FragmentFilterNasabahBottomSheetBinding
import com.am.bbsa.utils.KEY
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class FilterNasabahBottomSheet(private val callbackFilter: (String, String) -> Unit) :
    BottomSheetDialogFragment() {

    private var _binding: FragmentFilterNasabahBottomSheetBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilterNasabahBottomSheetBinding.inflate(inflater, container, false)
        setupCheckBox()
        setupFilter()
        return binding.root
    }

    private fun setupCheckBox() {
        binding.checkboxNewNasabah.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.checkboxOldNasabah.isChecked = false
                binding.checkboxAsc.isChecked = false
                binding.checkboxDesc.isChecked = false
            }
        }
        binding.checkboxOldNasabah.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.checkboxNewNasabah.isChecked = false
                binding.checkboxAsc.isChecked = false
                binding.checkboxDesc.isChecked = false
            }
        }
        binding.checkboxAsc.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.checkboxDesc.isChecked = false
                binding.checkboxNewNasabah.isChecked = false
                binding.checkboxOldNasabah.isChecked =false
            }
        }
        binding.checkboxDesc.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.checkboxAsc.isChecked = false
                binding.checkboxNewNasabah.isChecked = false
                binding.checkboxOldNasabah.isChecked =false
            }
        }
    }


    private fun setupFilter() {
        binding.buttonSave.setOnClickListener {
            var isCheckedCreated = ""
            var isCheckedName = ""
            if (binding.checkboxNewNasabah.isChecked) isCheckedCreated = KEY.KEY_FILTER_NASABAH_BARU
            if (binding.checkboxOldNasabah.isChecked) isCheckedCreated = KEY.KEY_FILTER_NASABAH_LAMA
            if (binding.checkboxAsc.isChecked) isCheckedName = KEY.KEY_FILTER_ASC_NAME
            if (binding.checkboxDesc.isChecked) isCheckedName = KEY.KEY_FILTER_DESC_NAME

            callbackFilter.invoke(isCheckedCreated, isCheckedName)
            dismiss()
        }
        binding.buttonClose.setOnClickListener {
            dismiss()
        }
    }

    companion object {
        fun show(fragmentManager: FragmentManager, callbackFilter: (String, String) -> Unit) {
            val bottomSheetFilter = FilterNasabahBottomSheet(callbackFilter)
            bottomSheetFilter.show(fragmentManager, bottomSheetFilter.tag)
        }
    }
}