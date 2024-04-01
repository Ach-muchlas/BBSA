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

	@field:SerializedName("nama_sampah")
	val namaSampah: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("foto_profile_user")
	val fotoProfileUser: Any? = null,

	@field:SerializedName("berat_sampah")
	val beratSampah: Int? = null,

	@field:SerializedName("jenis_sampah")
	val jenisSampah: String? = null,

	@field:SerializedName("status")
	val status: String? = null,

	@field:SerializedName("username")
	val username: String? = null
)
