package com.am.bbsa.data.body

import com.google.gson.annotations.SerializedName

data class SampahInformationBody(
    @field:SerializedName("nama") val nama: String,
    @field:SerializedName("jenis") val type: String,
    @field:SerializedName("harga_per_kg") val price: Int,
    @field:SerializedName("foto") val imageUrl: String,
)
