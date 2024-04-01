package com.am.bbsa.data.response

import com.google.gson.annotations.SerializedName

data class NasabahResponse(

    @field:SerializedName("data")
    val data: List<DataItemNasabah?>? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("status")
    val status: Boolean? = null
)

data class DataItemNasabah(

    @field:SerializedName("saldo_aktual")
    val balance: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("nomor_telephone")
    val phoneNumber: String,

    @field:SerializedName("nomor_register")
    val number_register: String,

    @field:SerializedName("status")
    val status: String,
)
