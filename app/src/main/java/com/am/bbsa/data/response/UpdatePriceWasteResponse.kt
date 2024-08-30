package com.am.bbsa.data.response

import com.google.gson.annotations.SerializedName

data class UpdatePriceWasteResponse(

	@field:SerializedName("data")
	val data: List<DataItem?>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
)

data class DataItem(

	@field:SerializedName("harga_baru")
	val hargaBaru: Int? = null,

	@field:SerializedName("sampah_id")
	val sampahId: Int? = null
)
