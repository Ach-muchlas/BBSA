package com.am.bbsa.data.response

import com.google.gson.annotations.SerializedName

data class RegisterResponse(

    @field:SerializedName("data")
	val data: DataItemRegister? = null,

    @field:SerializedName("message")
	val message: String? = null,

    @field:SerializedName("status")
	val status: String? = null
)

data class DataItemRegister(

	@field:SerializedName("foto_profil")
	val photoProfile: Any? = null,

	@field:SerializedName("role")
	val role: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("jenis_kelamin")
	val gender: String? = null,

	@field:SerializedName("nomor_telephone")
	val phoneNumber: String? = null,

	@field:SerializedName("alamat")
	val address: String? = null,
)
