package com.am.bbsa.data.response

import com.google.gson.annotations.SerializedName

data class SchedulePickUpWasteResponse(

	@field:SerializedName("data")
	val data: DataItemSchedule? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
)

data class DataItemSchedule(

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("tanggal")
	val tanggal: String? = null
)
