package com.am.bbsa.data.response.total_berat_sampah

import com.google.gson.annotations.SerializedName

data class TotalWastePerWeeksResponse(

	@field:SerializedName("data")
	val data: DataSampahPerWeeks? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
)

data class DataSampahPerWeeks(

	@field:SerializedName("week_4")
	val week4: Int? = null,

	@field:SerializedName("week_3")
	val week3: Int? = null,

	@field:SerializedName("week_2")
	val week2: Int? = null,

	@field:SerializedName("week_1")
	val week1: Int? = null
)
