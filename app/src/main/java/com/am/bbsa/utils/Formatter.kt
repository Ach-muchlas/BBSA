package com.am.bbsa.utils

import java.text.DecimalFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale
import java.util.TimeZone

object Formatter {
    fun formatCurrency(amount: Int): String {
        val decimal = DecimalFormat("#,###.##")
        return "Rp. ${decimal.format(amount)}"
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

}