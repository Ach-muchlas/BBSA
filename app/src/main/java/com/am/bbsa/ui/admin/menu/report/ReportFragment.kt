package com.am.bbsa.ui.admin.menu.report

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.pdf.PdfDocument
import android.os.Bundle
import android.os.Environment
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.am.bbsa.adapter.menu.ReportNasabahAdapter
import com.am.bbsa.adapter.menu.report.NasabahDepositWasteReportsAdapter
import com.am.bbsa.adapter.menu.report.NasabahWithdrawBalanceReportsAdapter
import com.am.bbsa.data.response.DataItemNasabah
import com.am.bbsa.data.response.DetailNasabahResponse
import com.am.bbsa.data.response.report.DataItemNasabahWasteDepositReport
import com.am.bbsa.data.response.report.NasabahWasteDepositReportsResponse
import com.am.bbsa.data.response.report.NasabahWithdrawBalanceReportsResponse
import com.am.bbsa.databinding.FragmentContentReportBinding
import com.am.bbsa.databinding.FragmentReportBinding
import com.am.bbsa.service.source.Status
import com.am.bbsa.ui.admin.menu.MenuViewModel
import com.am.bbsa.ui.auth.AuthViewModel
import com.am.bbsa.ui.bottom_sheet.FilterNasabahBottomSheet
import com.am.bbsa.utils.Formatter
import com.am.bbsa.utils.UiHandler
import org.koin.android.ext.android.inject
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class ReportFragment : Fragment() {

    private var _binding: FragmentReportBinding? = null
    private val binding get() = _binding!!
    private val authViewModel: AuthViewModel by inject()
    private val viewModel: MenuViewModel by inject()
    private val token: String by lazy { authViewModel.getCredentialUser()?.token.toString() }
    private var currentNasabahId: Int = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReportBinding.inflate(inflater, container, false)
        setupGetDataFromApi()
        initialize()
        searchNasabah()
        setupNavigation()
        return binding.root
    }

    private fun initialize() {
        UiHandler.setupVisibilityBottomNavigationAdmin(activity, true)
    }

    private fun setupNavigation() {
        binding.buttonBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.textFilter.setOnClickListener {
            FilterNasabahBottomSheet.show(childFragmentManager) { isCheckedCreated, isCheckedName ->
                setupFilter(isCheckedCreated, isCheckedName)
            }
        }
    }

    private fun searchNasabah() {
        binding.edtSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(text: Editable?) {
                if (text.isNullOrEmpty()) {
                    setupGetDataFromApi()
                } else {
                    setupSearchNasabahByName(text.toString())
                }
            }
        })
    }

    private fun setupSearchNasabahByName(name: String) {
        viewModel.searchNasabahByName(token, name).observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                Status.LOADING -> {}
                Status.SUCCESS -> {
                    setupNasabahAdapter(resource.data?.data)
                }

                Status.ERROR -> {}
            }
        }
    }


    private fun setupFilter(isCheckedCreated: String, isCheckedName: String) {
        viewModel.showNasabahFilterCreated(token, isCheckedCreated, isCheckedName)
            .observe(viewLifecycleOwner) { resource ->
                when (resource.status) {
                    Status.LOADING -> {}
                    Status.SUCCESS -> {
                        setupNasabahAdapter(resource.data)
                    }

                    Status.ERROR -> {
                        UiHandler.toastErrorMessage(requireContext(), resource.message.toString())
                    }
                }
            }
    }

    private fun setupGetDataFromApi() {
        viewModel.showAllNasabah(token).observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                Status.LOADING -> {}
                Status.SUCCESS -> {
                    setupNasabahAdapter(resource.data?.data)
                }

                Status.ERROR -> {
                    UiHandler.toastErrorMessage(requireContext(), resource.message.toString())
                }
            }
        }
    }

    private fun setupNasabahAdapter(data: List<DataItemNasabah?>?) {
        val adapter = ReportNasabahAdapter().apply {
            submitList(data)
            onDownloadClickListener { id ->
                currentNasabahId = id
                fetchDataAndCreatePdf(id)
            }
        }
        binding.recyclerViewNasabah.let {
            it.adapter = adapter
            it.layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun fetchDataAndCreatePdf(userId: Int) {
        val nasabahId = userId - 1
        viewModel.showDetailNasabahById(nasabahId, token)
            .observe(viewLifecycleOwner) { resourceDetailNasabah ->
                when (resourceDetailNasabah.status) {
                    Status.LOADING -> {}
                    Status.SUCCESS -> {
                        val dataDetailNasabah = resourceDetailNasabah.data
                        viewModel.showReportDepositWasteNasabah(token, userId)
                            .observe(viewLifecycleOwner) { resourceReportDepositWaste ->
                                when (resourceReportDepositWaste.status) {
                                    Status.LOADING -> {}
                                    Status.SUCCESS -> {
                                        val dataReportDepositWaste = resourceReportDepositWaste.data
                                        viewModel.showReportWithdrawBalanceNasabah(token, userId)
                                            .observe(viewLifecycleOwner) { resourceWithdrawBalance ->
                                                when (resourceWithdrawBalance.status) {
                                                    Status.LOADING -> {}
                                                    Status.SUCCESS -> {
                                                        val withdrawReports =
                                                            resourceWithdrawBalance.data
                                                        createPdf(
                                                            dataDetailNasabah,
                                                            dataReportDepositWaste,
                                                            withdrawReports
                                                        )
                                                    }

                                                    Status.ERROR -> {
                                                        UiHandler.toastErrorMessage(
                                                            requireContext(),
                                                            resourceWithdrawBalance.message.toString()
                                                        )
                                                    }
                                                }
                                            }
                                    }

                                    Status.ERROR -> {
                                        UiHandler.toastErrorMessage(
                                            requireContext(),
                                            resourceReportDepositWaste.message.toString()
                                        )
                                    }
                                }
                            }
                    }

                    Status.ERROR -> {
                        UiHandler.toastErrorMessage(
                            requireContext(),
                            resourceDetailNasabah.message.toString()
                        )
                    }
                }
            }
    }

    private fun setupAdapterReportPdf(
        binding: FragmentContentReportBinding,
        detailNasabah: DetailNasabahResponse?,
        depositReports: NasabahWasteDepositReportsResponse?,
        withdrawReports: NasabahWithdrawBalanceReportsResponse?
    ) {
        // setupView data user
        detailNasabah.let { data ->
            binding.textValueName.text = data?.data?.user?.name
            binding.textValueNoTelephone.text = data?.data?.user?.phoneNumber
            binding.textValueNoRegis.text = data?.data?.noRegis
            binding.textValueBalance.text = Formatter.formatCurrency(data?.data?.balance ?: 0)
        }

        depositReports.let { data ->
            val adapter =
                NasabahDepositWasteReportsAdapter(data?.data as List<DataItemNasabahWasteDepositReport>)
            binding.recyclerViewDepositWaste.let {
                it.adapter = adapter
                it.layoutManager = LinearLayoutManager(requireContext())
            }
        }

        withdrawReports.let { data ->
            val adapter = NasabahWithdrawBalanceReportsAdapter().apply { submitList(data?.data) }
            binding.recyclerViewWithdrawBalance.let {
                it.adapter = adapter
                it.layoutManager = LinearLayoutManager(requireContext())
            }
        }
    }

    private fun createPdf(
        detailNasabah: DetailNasabahResponse?,
        depositReports: NasabahWasteDepositReportsResponse?,
        withdrawReports: NasabahWithdrawBalanceReportsResponse?
    ) {
        val inflater = LayoutInflater.from(requireContext())
        val contextInflater = FragmentContentReportBinding.inflate(inflater)

        setupAdapterReportPdf(contextInflater, detailNasabah, depositReports, withdrawReports)

        contextInflater.root.measure(
            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        )

        contextInflater.root.layout(
            0,
            0,
            contextInflater.root.measuredWidth,
            contextInflater.root.measuredHeight
        )

        val bitmap = Bitmap.createBitmap(
            contextInflater.root.measuredWidth,
            contextInflater.root.measuredHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        contextInflater.root.draw(canvas)

        val pdfDocument = PdfDocument()
        val pageInfo = PdfDocument.PageInfo.Builder(
            bitmap.width,
            bitmap.height,
            1
        ).create()
        val page = pdfDocument.startPage(pageInfo)
        val pdfCanvas = page.canvas
        pdfCanvas.drawBitmap(bitmap, 0f, 0f, null)
        pdfDocument.finishPage(page)

        val filePath = File(
            requireContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS),
            "laporan_transaksi.pdf"
        )

        try {
            pdfDocument.writeTo(FileOutputStream(filePath))
            pdfDocument.close()
            UiHandler.toastSuccessMessage(requireContext(), "PDF Created successfully")
            openPdf(filePath)
        } catch (e: IOException) {
            e.printStackTrace()
            UiHandler.toastSuccessMessage(requireContext(), "Error Creating PDF")
        }
    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun openPdf(filePath: File) {
        val uri = FileProvider.getUriForFile(
            requireContext(),
            "${requireContext().packageName}.fileprovider",
            filePath
        )

        val intent = Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(uri, "application/pdf")
            flags = Intent.FLAG_ACTIVITY_NO_HISTORY or Intent.FLAG_GRANT_READ_URI_PERMISSION
        }

        if (intent.resolveActivity(requireActivity().packageManager) != null) {
            startActivity(intent)
        } else {
            UiHandler.toastErrorMessage(requireContext(), "No application available to view PDF")
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, fetch data and create PDF again if necessary
            } else {
                UiHandler.toastErrorMessage(requireContext(), "Permission Denied")
            }
        }
    }

    companion object {
        const val PERMISSION_REQUEST_CODE = 1001
    }

    override fun onDestroy() {
        super.onDestroy()
        UiHandler.setupVisibilityBottomNavigationAdmin(activity, false)
    }
}