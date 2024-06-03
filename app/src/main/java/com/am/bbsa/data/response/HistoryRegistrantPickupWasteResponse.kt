package com.am.bbsa.data.response

import com.google.gson.annotations.SerializedName

data class HistoryRegistrantPickupWasteResponse(

	@field:SerializedName("data")
	val data: List<DataItemHistoryRegistrantPickupWaste?>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
)

data class DataItemHistoryRegistrantPickupWaste(

	@field:SerializedName("foto")
	val foto: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("jadwal_jemput_sampah_id")
	val jadwalJemputSampahId: Int? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("deskripsi")
	val deskripsi: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)
