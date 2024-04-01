package com.am.bbsa.data.response

import com.google.gson.annotations.SerializedName

data class TotalSaldoResponse(

	@field:SerializedName("data")
	val data: Data,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
)

data class Data(

	@field:SerializedName("total_saldo")
	val totalSaldo: Int
)
