package com.am.bbsa.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class DepositWeighingResponse(

	@field:SerializedName("data")
	val data: List<DataItemDepositWeighing?>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
)

@Parcelize
data class DataItemDepositWeighing(

	@field:SerializedName("total_setoran")
	val totalSetoran: Int? = null,

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("tanggal")
	val tanggal: String? = null,

	@field:SerializedName("bukti_setor")
	val buktiSetor: String? = null,

	@field:SerializedName("status")
	val status: String? = null,

	@field:SerializedName("username")
	val username: String? = null
) : Parcelable
