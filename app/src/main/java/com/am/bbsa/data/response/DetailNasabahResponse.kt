package com.am.bbsa.data.response

import com.google.gson.annotations.SerializedName

data class DetailNasabahResponse(

    @field:SerializedName("data")
	val data: DataItemDetailNasabah? = null,

    @field:SerializedName("message")
	val message: String? = null,

    @field:SerializedName("status")
	val status: Boolean? = null
)

data class DataItemDetailNasabah(

    @field:SerializedName("NIK")
	val NIK: String? = null,

    @field:SerializedName("saldo_aktual")
	val balance: Int? = null,

    @field:SerializedName("saldo_prediksi")
	val temporaryBalance: Int? = null,

    @field:SerializedName("user_id")
	val userId: Int? = null,

    @field:SerializedName("created_at")
	val createdAt: String? = null,

    @field:SerializedName("id")
	val id: Int? = null,

    @field:SerializedName("nomor_register")
	val noRegis: String? = null,

    @field:SerializedName("user")
	val user: User? = null,

    @field:SerializedName("status")
	val status: String? = null
)

data class User(

	@field:SerializedName("foto_profil")
	val photoProfile: Any? = null,

	@field:SerializedName("role")
	val role: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("nomor_telephone")
	val phoneNumber: String? = null,

	@field:SerializedName("jenis_kelamin")
	val gender: String? = null,

	@field:SerializedName("alamat")
	val address: String? = null
)
