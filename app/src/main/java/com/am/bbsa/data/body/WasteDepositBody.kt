package com.am.bbsa.data.body

import com.google.gson.annotations.SerializedName

data class WasteDepositBody(
    @field:SerializedName("bukti_setor") val photo: String,
)

data class WasteDepositAdminBody(
    @field:SerializedName("user_id") val user_id: Int,
    @field:SerializedName("bukti_setor") val photo: String,
)