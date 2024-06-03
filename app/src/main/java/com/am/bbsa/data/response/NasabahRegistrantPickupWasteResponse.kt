package com.am.bbsa.data.response

import com.google.gson.annotations.SerializedName

data class NasabahRegistrantPickupWasteResponse(

    @field:SerializedName("data")
    val data: List<DataItemNasabahRegistrantPickupWaste?>? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("status")
    val status: String? = null
)

data class DataItemNasabahRegistrantPickupWaste(

    @field:SerializedName("user_foto_profil")
    val photoProfile: String? = null,

    @field:SerializedName("user_id")
    val userId: Int? = null,

    @field:SerializedName("user_name")
    val userName: String? = null,

    @field:SerializedName("jadwal_jemput_sampah_id")
    val jadwalJemputSampahId: Int? = null,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("tanggal")
    val tanggal: String? = null,

    @field:SerializedName("status")
    val status: String? = null
)
