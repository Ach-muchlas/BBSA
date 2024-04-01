package com.am.bbsa.data.response

import com.google.gson.annotations.SerializedName

data class HistoryDepositResponse(

    @field:SerializedName("data")
    val data: List<DataItemHistoryDeposit?>? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("status")
    val status: Boolean? = null
)

data class DataDetailUser(

    @field:SerializedName("foto_profil")
    val fotoProfil: Any? = null,

    @field:SerializedName("role")
    val role: String? = null,

    @field:SerializedName("updated_at")
    val updatedAt: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("created_at")
    val createdAt: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("nomor_telephone")
    val nomorTelephone: String? = null,

    @field:SerializedName("jenis_kelamin")
    val jenisKelamin: String? = null,

    @field:SerializedName("alamat")
    val alamat: String? = null
)

data class DataItemHistoryDeposit(

    @field:SerializedName("total_setoran")
    val totalSetoran: Int? = null,

    @field:SerializedName("updated_at")
    val updatedAt: String? = null,

    @field:SerializedName("user_id")
    val userId: Int? = null,

    @field:SerializedName("created_at")
    val createdAt: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("tanggal")
    val tanggal: String? = null,

    @field:SerializedName("bukti_setor")
    val buktiSetor: String? = null,

    @field:SerializedName("user")
    val user: DataDetailUser? = null,

    @field:SerializedName("status")
    val status: String? = null
)
