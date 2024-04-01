package com.am.bbsa.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class WasteDepositResponse(

	@field:SerializedName("data")
	val data: List<DataItemWasteDeposit?>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
)

@Parcelize
data class DataItemWasteDeposit(

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

	@field:SerializedName("tanggal")
	val date: String? = null,

	@field:SerializedName("username")
	val username: String? = null,

	@field:SerializedName("bukti_setor")
	val buktiSetor: String? = null,

	@field:SerializedName("status")
	val status: String? = null,

	@field:SerializedName("foto_profil")
	val photoProfile: String? = null
) : Parcelable
