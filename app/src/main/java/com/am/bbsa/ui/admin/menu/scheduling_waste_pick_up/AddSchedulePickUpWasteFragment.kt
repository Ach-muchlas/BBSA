package com.am.bbsa.ui.admin.menu.scheduling_waste_pick_up

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.am.bbsa.R
import com.am.bbsa.databinding.FragmentAddSchedulePickUpWasteBinding
import com.am.bbsa.service.source.Status
import com.am.bbsa.ui.admin.menu.MenuViewModel
import com.am.bbsa.ui.auth.AuthViewModel
import com.am.bbsa.utils.Formatter
import com.am.bbsa.utils.UiHandler
import com.am.bbsa.utils.toISO8601String
import org.koin.android.ext.android.inject
import java.util.Calendar
import java.util.Date

class AddSchedulePickUpWasteFragment : Fragment() {

    private var _binding: FragmentAddSchedulePickUpWasteBinding? = null
    private val binding get() = _binding!!

    private val authViewModel: AuthViewModel by inject()
    private val viewModel: MenuViewModel by inject()


    private lateinit var selectedDate: Date
    private val calendar = Calendar.getInstance()
    private val token: String by lazy {
        authViewModel.getCredentialUser()?.token.toString()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddSchedulePickUpWasteBinding.inflate(inflater, container, false)
        initialize()
        setupClickableEditText()
        setupNavigation()
        return binding.root
    }

    private fun initialize() {
        selectedDate = Date()
        binding.viewAppBar.textTitleAppBar.text = getString(R.string.add_scheduling_pick_up)
        UiHandler.setupVisibilityBottomNavigationAdmin(activity, true)
    }

    private fun setupNavigation(){
        binding.viewAppBar.buttonBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.buttonSave.setOnClickListener {
            setupPostScheduleToApi()
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupClickableEditText() {
        binding.edtText.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                binding.edtText.clearFocus()
                showDatePickerDialog()
                return@setOnTouchListener true
            }
            return@setOnTouchListener false
        }
    }

    private fun showDatePickerDialog() {
        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, year, month, dayOfMonth ->
                // Set the selected date to the Calendar
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                // Update the EditText with the selected date
                selectedDate = calendar.time
                Formatter.formatDateToEditTextValue(binding.edtText, calendar)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        // Show the date picker dialog
        datePickerDialog.show()
    }

    private fun setupPostScheduleToApi() {
        viewModel.createSchedulePickupWaste(token, selectedDate.toISO8601String())
            .observe(viewLifecycleOwner) { resource ->
                when (resource.status) {
                    Status.LOADING -> {}
                    Status.SUCCESS -> {
                        UiHandler.toastSuccessMessage(
                            requireContext(),
                            resource.data?.message.toString()
                        )
                        findNavController().popBackStack()
                    }

                    Status.ERROR -> {
                        UiHandler.toastErrorMessage(requireContext(), resource.message.toString())
                    }
                }
            }
    }
}