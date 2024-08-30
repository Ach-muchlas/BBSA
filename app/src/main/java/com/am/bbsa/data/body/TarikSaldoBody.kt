package com.am.bbsa.data.body

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class TarikSaldoBody(
    @field:SerializedName("bank_code") val bankCode: String,
    @field:SerializedName("account_holder_name") val accountName: String,
    @field:SerializedName("account_number") val accountNumber: String,
    @field:SerializedName("amount") val amount: Int,
    @field:SerializedName("otp") val otp: Int,
): Parcelable