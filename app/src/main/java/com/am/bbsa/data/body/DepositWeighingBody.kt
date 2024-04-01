package com.am.bbsa.data.body

import com.google.gson.annotations.SerializedName

data class DepositWeighingBody(
    @field:SerializedName("setor_sampah_id") val idDepositWaste: Int,
    @field:SerializedName("sampah_id") val idWaste: List<Int>,
    @field:SerializedName("berat_sampah") val wasteWeight: List<Int>,
)
