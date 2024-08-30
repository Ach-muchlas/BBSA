package com.am.bbsa.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class SampahResponse(

	@field:SerializedName("data")
	val data: List<DataItemSampah?>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
)

@Parcelize
data class DataItemSampah(

	@field:SerializedName("nama")
	val name: String? = null,

	@field:SerializedName("foto")
	val photo: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("jenis")
	val type: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("harga")
	var price: Int? = null
) : Parcelable
