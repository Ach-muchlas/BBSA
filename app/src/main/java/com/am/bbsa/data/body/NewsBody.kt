package com.am.bbsa.data.body

import com.google.gson.annotations.SerializedName

data class NewsBody(
    @field:SerializedName("judul") val title: String,
    @field:SerializedName("deskripsi") val description: String,
    @field:SerializedName("foto") val photo: String,
)