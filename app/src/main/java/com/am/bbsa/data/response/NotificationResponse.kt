package com.am.bbsa.data.response

import com.google.gson.annotations.SerializedName

data class NotificationResponse(

	@field:SerializedName("data")
	val data: List<DataItemNotification?>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
)

data class DataItemNotification(

	@field:SerializedName("pesan")
	val pesan: String? = null,

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("kategori")
	val kategori: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("judul")
	val judul: String? = null
)
