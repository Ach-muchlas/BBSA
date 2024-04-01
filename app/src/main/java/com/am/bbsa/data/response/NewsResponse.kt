package com.am.bbsa.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class NewsResponse(

	@field:SerializedName("data")
	val data: List<DataItemNews?>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
)

@Parcelize
data class DataItemNews(

	@field:SerializedName("foto")
	val photo: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("deskripsi")
	val description: String? = null,

	@field:SerializedName("judul")
	val title: String? = null
) : Parcelable
