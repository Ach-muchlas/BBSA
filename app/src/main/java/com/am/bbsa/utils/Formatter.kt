package com.am.bbsa.utils

import android.widget.EditText
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

object Formatter {
    fun formatCurrency(amount: Int): String {
        val decimal = DecimalFormat("#,###.##")
        return "Rp. ${decimal.format(amount)}"
    }

    fun formatKg(amount: Int): String {
        return "$amount Kg"
    }

    fun formatDateTime(dateTimeFormatted: LocalDateTime): CharSequence {
        val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.getDefault())
        return formatter.format(dateTimeFormatted)
    }

    fun formatDate(currentDateString: String): String {
        val instant = Instant.parse(currentDateString)
        val zoneId = ZoneId.of("Asia/Jakarta")
        val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy")
            .withZone(ZoneId.of(TimeZone.getDefault().id))
        return formatter.format(instant)
    }

    fun formatDateToEditTextValue(editText: EditText, calendar: Calendar) {
        val dateFormat = SimpleDateFormat("d MMM yyyy", Locale.getDefault())
        editText.setText(dateFormat.format(calendar.time))
    }


    fun formatDate2(dateStr: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
        val date = inputFormat.parse(dateStr)
        return outputFormat.format(date)
    }

}