package com.am.bbsa.ui.admin.home

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.am.bbsa.R
import com.am.bbsa.data.response.TotalSaldoResponse
import com.am.bbsa.data.response.UserResponse
import com.am.bbsa.data.response.total_berat_sampah.TotalWastePerWeeksResponse
import com.am.bbsa.data.response.total_berat_sampah.TotalWasteResponse
import com.am.bbsa.databinding.FragmentHomeAdminBinding
import com.am.bbsa.service.source.Status
import com.am.bbsa.ui.auth.AuthViewModel
import com.am.bbsa.utils.Formatter
import com.am.bbsa.utils.PercentageValueFormatter
import com.am.bbsa.utils.UiHandler
import com.bumptech.glide.Glide
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import org.koin.android.ext.android.inject

class HomeAdminFragment : Fragment() {
    private var _binding: FragmentHomeAdminBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeAdminViewModel by inject()
    private val authViewModel: AuthViewModel by inject()
    private val token by lazy {
        authViewModel.getCredentialUser()?.token.toString()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeAdminBinding.inflate(inflater, container, false)
        displayAppBarView()
        setupView()
        return binding.root
    }

    private fun setupView() {
        with(binding.cardBalance) {
            textBalance1.text = getString(R.string.total_saldo)
            textPredict.text = getString(R.string.total_waste)
        }
    }

    private fun setupBarChart(data: TotalWastePerWeeksResponse?) {
        val barChart = binding.barChart
        val weeks = ArrayList<String>()
        val entries = ArrayList<BarEntry>()

        val weeks1 = (data?.data?.week1?.toFloat() ?: 0.0) as Float
        val weeks2 = (data?.data?.week2?.toFloat() ?: 0.0) as Float
        val weeks3 = (data?.data?.week3?.toFloat() ?: 0.0) as Float
        val weeks4 = (data?.data?.week4?.toFloat() ?: 0.0) as Float

        // Menambahkan label minggu
        weeks.add("Minggu 1")
        weeks.add("Minggu 2")
        weeks.add("Minggu 3")
        weeks.add("Minggu 4")

        // Menambahkan data ke dalam entries
        entries.add(BarEntry(0f, weeks1))
        entries.add(BarEntry(1f, weeks2))
        entries.add(BarEntry(2f, weeks3))
        entries.add(BarEntry(3f, weeks4))

        val barDataSet = BarDataSet(entries, "Weekly Data")
        barDataSet.colors = ArrayList(listOf(Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW))
        barDataSet.valueTextSize = 12f

        barChart.data = BarData(barDataSet)

        // Pengaturan sumbu x
        val xAxis = barChart.xAxis
        xAxis.valueFormatter = IndexAxisValueFormatter(weeks)
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.granularity = 1f // Jarak minimum antara label
        xAxis.setDrawGridLines(false)
        xAxis.setDrawAxisLine(true)

        // Pengaturan sumbu y
        val yAxisRight = barChart.axisRight
        yAxisRight.isEnabled = false
        val yAxisLeft = barChart.axisLeft
        yAxisLeft.axisMinimum = 0f

        barChart.setFitBars(true)
        barChart.description.isEnabled = false
        barChart.animateY(1000)
        barChart.invalidate()
    }

    private fun setupPieChart(entries: List<PieEntry>) {
        val pieChart = binding.chart
        val labels = ArrayList<String>()
        labels.add("Data")

        val dataSet = PieDataSet(entries, "")
        val colors: ArrayList<Int> = ArrayList()
        colors.add(resources.getColor(R.color.purple_200))
        colors.add(resources.getColor(R.color.deep_green))
        colors.add(resources.getColor(R.color.red))
        colors.add(resources.getColor(org.koin.android.R.color.material_blue_grey_800))
        colors.add(resources.getColor(R.color.blue))
        colors.add(resources.getColor(R.color.sage_green))
        colors.add(resources.getColor(R.color.gun_metal))

        dataSet.colors = colors
        dataSet.valueFormatter = PercentageValueFormatter()
        dataSet.valueTextSize = 14f
        dataSet.valueTextColor = Color.WHITE

        val data = PieData(dataSet)
        pieChart.data = data
        pieChart.description.isEnabled = false
        pieChart.setUsePercentValues(true)
        pieChart.invalidate()
    }

    private fun displayAppBarView() {
        viewModel.showTotalWaste(token).observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                Status.LOADING -> {
                    showShimmer(true)
                }

                Status.SUCCESS -> {
                    val entries = ArrayList<PieEntry>()
                    resource.data?.data?.jenisSampahInfo?.forEach { sampah ->
                        val dataPieChart = sampah?.totalJenisBeratSampah?.toFloat()
                        entries.add(PieEntry(dataPieChart ?: 0f, sampah?.jenisSampah))
                    }

                    setupPieChart(entries)
                    showShimmer(false)
                    setupIsVisibilityView()
                    setupViewTotalWaste(resource.data)
                }

                Status.ERROR -> {
                    UiHandler.toastErrorMessage(requireContext(), resource.message.toString())
                }
            }
        }

        viewModel.showTotalWastePerWeeks(token).observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                Status.LOADING -> {}
                Status.SUCCESS -> {
                    setupBarChart(resource.data)
                }

                Status.ERROR -> {
                    UiHandler.toastErrorMessage(requireContext(), resource.message.toString())
                }
            }
        }
        // displays data credential user (photo and name)
        viewModel.showDataUser(token).observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                Status.LOADING -> {
                    showShimmer(true)
                }

                Status.SUCCESS -> {
                    showShimmer(false)
                    setupIsVisibilityView()
                    setupViewCredentialUser(resource.data)
                }

                Status.ERROR -> {
                    showShimmer(false)
                    setupIsVisibilityView()
                    UiHandler.toastErrorMessage(requireContext(), resource.message.toString())
                }
            }
        }

        // displays data balance
        viewModel.showAllActualBalance(token).observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                Status.LOADING -> {
                    showShimmer(true)
                }

                Status.SUCCESS -> {
                    showShimmer(false)
                    setupIsVisibilityView()
                    setupViewBalance(resource.data)
                }

                Status.ERROR -> {
                    showShimmer(false)
                    setupIsVisibilityView()
                    UiHandler.toastErrorMessage(requireContext(), resource.message.toString())
                }
            }
        }
    }


    private fun setupViewCredentialUser(data: UserResponse?) {
        if (data?.data?.fotoProfil.isNullOrEmpty()) {
            if (data?.data?.jenisKelamin == "Perempuan") {
                binding.viewAppBar.imageProfile.setImageResource(R.drawable.icon_profile_women)
            } else {
                binding.viewAppBar.imageProfile.setImageResource(R.drawable.icon_profile_man)
            }
        } else {
            Glide.with(requireContext()).load(data?.data?.fotoProfil)
                .into(binding.viewAppBar.imageProfile)
        }
        binding.viewAppBar.textName.text = data?.data?.name
    }

    private fun showShimmer(isVisible: Boolean) {
        with(binding.cardBalance) {
            UiHandler.manageShimmer(shimmerContainerBalance, isVisible)
            UiHandler.manageShimmer(shimmerContainerTemporaryBalance, isVisible)
        }
        with(binding.viewAppBar) {
            UiHandler.manageShimmer(shimmerContainerImage, isVisible)
            UiHandler.manageShimmer(shimmerContainerName, isVisible)
        }
    }

    private fun setupIsVisibilityView() {
        binding.viewAppBar.apply {
            textName.visibility = View.VISIBLE
            imageProfile.visibility = View.VISIBLE
        }
        binding.cardBalance.apply {
            textBalance.visibility = View.VISIBLE
            textValuePredict.visibility = View.VISIBLE
        }
    }

    private fun setupViewBalance(data: TotalSaldoResponse?) {
        binding.cardBalance.textBalance.text = Formatter.formatCurrency(data?.data?.totalSaldo ?: 0)
    }

    private fun setupViewTotalWaste(data: TotalWasteResponse?) {
        binding.cardBalance.textValuePredict.text =
            Formatter.formatKg(data?.data?.totalBeratSemuaSampah ?: 0)
    }
}
