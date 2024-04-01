package com.am.bbsa.data.response

import com.google.gson.annotations.SerializedName

data class UserResponse(

	@field:SerializedName("data")
	val data: DataItemUser? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
)

data class DataItemUser(

	@field:SerializedName("foto_profil")
	val fotoProfil: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("nomor_telephone")
	val nomorTelephone: String? = null,

	@field:SerializedName("jenis_kelamin")
	val jenisKelamin: String? = null,

	@field:SerializedName("nasabah")
	val nasabah: DataItemDetailNasabah? = null,

	@field:SerializedName("alamat")
	val alamat: String? = null
)
