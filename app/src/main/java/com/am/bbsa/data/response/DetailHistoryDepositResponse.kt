package com.am.bbsa.data.response

import com.google.gson.annotations.SerializedName

data class DetailHistoryDepositResponse(

    @field:SerializedName("data")
    val data: List<DataItemDetailHistoryDeposit?>? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("status")
    val status: Boolean? = null
)


data class DataItemDetailHistoryDeposit(

    @field:SerializedName("total_setoran")
    val totalSetoran: Int? = null,

    @field:SerializedName("id_setoran")
    val idSetoran: Int? = null,

    @field:SerializedName("created_at")
    val createdAt: String? = null,

    @field:SerializedName("foto_profile")
    val fotoProfile: String? = null,

    @field:SerializedName("status")
    val status: String? = null,

    @field:SerializedName("username")
    val username: String? = null,

    @field:SerializedName("sampah")
    val sampah: DataSampah? = null,

    @field:SerializedName("admin_name")
    val admin_name: String? = null,

    )

data class DataSampah(

    @field:SerializedName("nama_sampah")
    val name: String? = null,

    @field:SerializedName("foto_sampah")
    val photo: String? = null,


    @field:SerializedName("id_sampah")
    val id: Int,

    @field:SerializedName("harga_per_kg")
    var price: Int? = null,

    @field:SerializedName("berat_sampah")
    var weight: Int
)
