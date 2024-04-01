package com.am.bbsa.ui.admin.menu.deposit_weighing

import android.content.res.ColorStateList
import android.os.Bundle
import android.text.InputType
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.am.bbsa.R
import com.am.bbsa.data.response.DataItemDepositWeighing
import com.am.bbsa.data.response.DataItemSampah
import com.am.bbsa.databinding.FragmentDetailDepositWeighingBinding
import com.am.bbsa.service.source.Status
import com.am.bbsa.ui.admin.menu.MenuViewModel
import com.am.bbsa.ui.auth.AuthViewModel
import com.am.bbsa.utils.Formatter
import com.am.bbsa.utils.UiHandler
import com.bumptech.glide.Glide
import com.google.android.material.textfield.TextInputLayout
import org.koin.android.ext.android.inject

@Suppress("UNCHECKED_CAST")
class DetailDepositWeighingFragment : Fragment() {
    /*setup view binding*/
    private var _binding: FragmentDetailDepositWeighingBinding? = null
    private val binding get() = _binding!!

    /*initialize variable global scope*/
    private lateinit var adapterAutocomplete: ArrayAdapter<Pair<Int, String>>
    private var selectedIdTypeWaste: MutableMap<Int, Int> = mutableMapOf()
    private var selectedNameTypeWaste: String? = null
    private var listDataIdTypeWaste: MutableList<Int> = mutableListOf()
    private val autoCompleteList = mutableListOf<AutoCompleteTextView>()
    private val edtList = mutableListOf<EditText>()
    private val receiveBundle by lazy {
        arguments?.getParcelable<DataItemDepositWeighing>(DepositWeighingFragment.KEY_DEPOSIT_WEIGHING)
    }
    private val token by lazy {
        authViewModel.getCredentialUser()?.token.toString()
    }

    // initialize view model
    private val viewModel: MenuViewModel by inject()
    private val authViewModel: AuthViewModel by inject()

    // extension
    private val Int.dp: Int
        get() = resources.displayMetrics.density.toInt() * this

    private fun ArrayAdapter<Pair<Int, String>>.getItemByName(name: String): Pair<Int, String>? {
        for (i in 0 until count) {
            val item = getItem(i)
            if (item?.second == name) {
                return item
            }
        }
        return null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailDepositWeighingBinding.inflate(inflater, container, false)
        setupView()
        setupNavigation()
        setupDataDropdownTypeWaste()
        return binding.root
    }

    private fun setupView() {
        binding.viewAppbar.textTitleAppBar.text = getText(R.string.detail_deposit_weighing)
        UiHandler.setupVisibilityBottomNavigationAdmin(activity, true)
        binding.edtDate.isFocusable = false
        binding.edtDate.isFocusableInTouchMode = false
        binding.autoCompleteTypeWaste.isFocusable = false
        binding.autoCompleteTypeWaste.isFocusableInTouchMode = false
        setupDisplayInputDepositWeighing()
    }

    private fun setupNavigation() {
        binding.viewAppbar.buttonBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.buttonSave.setOnClickListener {
            setupPostDataWeighing()
        }
    }


    private fun setupPostDataWeighing() {
        val defaultWeight = binding.edtWeight.text.toString().toInt()

        val defaultTypeWasteName = binding.autoCompleteTypeWaste.text.toString()
        val defaultTypeWaste = adapterAutocomplete.getItemByName(defaultTypeWasteName)
        val selectedIdDefault = defaultTypeWaste?.first ?: 0
        val listWeight = mutableListOf<Int>()
        listWeight.add(defaultWeight)
        listDataIdTypeWaste.add(selectedIdDefault)
        autoCompleteList.forEachIndexed { _, autoCompleteTextView ->
            val id = selectedIdTypeWaste[autoCompleteTextView.id]
            id?.let { data ->
                listDataIdTypeWaste.add(data)
            }
        }
        edtList.forEach {
            val data = it.text.toString().toInt()
            listWeight.add(data)
        }

        viewModel.updateDepositWeighing(token, receiveBundle?.id!!, listDataIdTypeWaste, listWeight)
            .observe(viewLifecycleOwner) { resource ->
                when (resource.status) {
                    Status.LOADING -> {}
                    Status.SUCCESS -> {
                        // back
                        findNavController().popBackStack()
                        UiHandler.toastSuccessMessage(
                            requireContext(),
                            resource.data?.message.toString()
                        )
                    }

                    Status.ERROR -> {
                        UiHandler.toastErrorMessage(requireContext(), resource.message.toString())
                    }
                }

            }
    }

    private fun setupDisplayInputDepositWeighing() {
        binding.edtName.setText(receiveBundle?.username)
        binding.edtDate.setText(Formatter.formatDate(receiveBundle?.createdAt ?: ""))
        Glide.with(requireContext()).load(receiveBundle?.buktiSetor).into(binding.imageWaste)

        /*absolute edit text*/
        binding.textAddTypeWaste.setOnClickListener {
            addNewTypeWasteAndWeight()
            setupDataDropdownTypeWaste()
        }
    }

    private fun setupDropDown(autoComplete: AutoCompleteTextView, nasabah: List<DataItemSampah>) {
        val listNasabah = nasabah.map { Pair(it.id, it.name ?: "") }
        adapterAutocomplete = object :
            ArrayAdapter<Pair<Int, String>>(requireContext(), R.layout.dropdown_item, listNasabah) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getView(position, convertView, parent) as TextView
                view.text = listNasabah[position].second
                return view
            }
        }

        /*view dropdown*/
        autoComplete.setAdapter(adapterAutocomplete)
        autoComplete.onItemClickListener = AdapterView.OnItemClickListener { adapterView, _, i, _ ->
            val selectedItem = adapterView.getItemAtPosition(i) as? Pair<Int, String>
            selectedIdTypeWaste[autoComplete.id] = selectedItem?.first ?: 0
            selectedNameTypeWaste = selectedItem?.second

            autoComplete.setText(buildString { append(selectedNameTypeWaste) })
            setupDataDropdownTypeWaste()
        }
    }

    private fun setupDataDropdownTypeWaste() {
        viewModel.showAllInformationWaste(token).observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                Status.LOADING -> {}
                Status.SUCCESS -> {
                    val data = resource.data?.data as List<DataItemSampah>
                    val autoComplete = binding.autoCompleteTypeWaste
                    autoCompleteList.forEach {
                        setupDropDown(it, data)
                    }
                    setupDropDown(autoComplete, data)
                    adapterAutocomplete.notifyDataSetChanged()
                }

                Status.ERROR -> {
                    UiHandler.toastErrorMessage(requireContext(), resource.message.toString())
                }
            }
        }
    }

    private fun addNewTypeWasteAndWeight() {
        // added style dropdown
        val contextThemeWrapper = ContextThemeWrapper(
            requireContext(),
            com.google.android.material.R.style.Widget_MaterialComponents_TextInputLayout_OutlinedBox_ExposedDropdownMenu
        )

        // Added constraint to place new TextInputLayout between "textAddTypeWaste" and "edlWeight"
        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        // add margin
        params.setMargins(24, 5, 24, 0) // Set margin

        /*initialize text view type waste*/
        val newTextTypeWaste = TextView(contextThemeWrapper).apply {
            id = View.generateViewId()
            text = getString(R.string.type_waste)
            setTextAppearance(R.style.TextContent_Body_PopReg)
            layoutParams = params
        }
        /*initialize edt layout type waste*/
        val newTextInputTypeWasteLayout = TextInputLayout(contextThemeWrapper).apply {
            id = View.generateViewId()
            setPadding(0, 0, 0, 0)
            hint = getString(R.string.example_type_waste)
            setHintTextAppearance(R.style.TextContent_Body_PopMedium)
            layoutParams = params
        }
        /*initialize autocomplete (dropdown) type waste*/
        val newAutoComplete = AutoCompleteTextView(contextThemeWrapper).apply {
            /*Generate unique id */
            id = View.generateViewId()
            setBackgroundResource(R.drawable.custom_bg_edit_text)
            setTextAppearance(R.style.TextContent_Body_PopMedium)
            setPadding(15.dp, 0, 15.dp, 0)
            isFocusable = false
            isFocusableInTouchMode = false
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                60.dp
            )

        }
        /*initialize text view Weight*/
        val newTextWeightWaste = TextView(contextThemeWrapper).apply {
            id = View.generateViewId()
            text = getString(R.string.weight)
            setTextAppearance(R.style.TextContent_Body_PopReg)
            layoutParams = params
        }
        // initialize edt layout weight
        val newTextInputWeightWasteLayout = TextInputLayout(requireContext()).apply {
            id = View.generateViewId()
            hintTextColor =
                ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.grey))
            setPadding(0, 0, 0, 0)
            hint = getString(R.string.input_waste_weight)
            setHintTextAppearance(R.style.TextContent_Body_PopMedium)
            layoutParams = params
        }
        // initialize edt text weight
        val newEditText = EditText(requireContext()).apply {
            /*Generate unique id */
            id = View.generateViewId()
            setBackgroundResource(R.drawable.custom_bg_edit_text)
            setTextAppearance(R.style.TextContent_Body_PopMedium)
            setPadding(15.dp, 0, 15.dp, 0)
            inputType = InputType.TYPE_CLASS_NUMBER
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                60.dp
            )
        }

        // added Edit Text or AutoComplete to Edit Layout
        newTextInputTypeWasteLayout.addView(newAutoComplete)
        newTextInputWeightWasteLayout.addView(newEditText)

        // set position view
        var index = binding.containerLayout.indexOfChild(binding.textAddTypeWaste)
        binding.containerLayout.addView(newTextTypeWaste, index)
        index++
        binding.containerLayout.addView(newTextInputWeightWasteLayout, index)
        binding.containerLayout.addView(newTextWeightWaste, index)
        binding.containerLayout.addView(newTextInputTypeWasteLayout, index)

        // add value to mutable list
        autoCompleteList.add(newAutoComplete)
        edtList.add(newEditText)

    }
}