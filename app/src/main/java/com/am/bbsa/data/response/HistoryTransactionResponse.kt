package com.am.bbsa.data.response

import com.google.gson.annotations.SerializedName

data class HistoryTransactionResponse(

	@field:SerializedName("data")
	val data: List<DataItemHistoryTransaction?>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
)

data class DataItemHistoryTransaction(

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("deskripsi")
	val deskripsi: String? = null,

	@field:SerializedName("judul")
	val judul: String? = null
)
