package com.am.bbsa.utils

import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.ValueFormatter

class PercentageValueFormatter : ValueFormatter() {
    override fun getPieLabel(value: Float, pieEntry: PieEntry?): String {
        return "${value.toInt()}%"
    }
}
