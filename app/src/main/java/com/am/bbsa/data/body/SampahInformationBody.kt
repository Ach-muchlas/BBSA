package com.am.bbsa.data.body

import com.google.gson.annotations.SerializedName

data class SampahInformationBody(
    @field:SerializedName("nama") val name: String,
    @field:SerializedName("jenis") val type: String,
    @field:SerializedName("harga") val price: Int,
    @field:SerializedName("foto") val imageUrl: String,
)
