package com.am.bbsa.data.response

import com.google.gson.annotations.SerializedName

data class HistoryDepositWasteResponse(

	@field:SerializedName("data")
	val data: List<DataItemHistoryDepositWaste?>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
)

data class DataItemHistoryDepositWaste(

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

	@field:SerializedName("bukti_setor")
	val buktiSetor: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)
