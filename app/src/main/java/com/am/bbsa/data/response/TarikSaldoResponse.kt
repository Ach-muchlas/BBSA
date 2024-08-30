package com.am.bbsa.data.response

import com.google.gson.annotations.SerializedName

data class TarikSaldoResponse(

	@field:SerializedName("data")
	val data: DataItemTarikSaldo? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
)

data class DataItemTarikSaldo(

	@field:SerializedName("bank_code")
	val bankCode: String? = null,

	@field:SerializedName("amount")
	val amount: Int? = null,

	@field:SerializedName("user_id")
	val userId: String? = null,

	@field:SerializedName("account_holder_name")
	val accountHolderName: String? = null,

	@field:SerializedName("external_id")
	val externalId: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("disbursement_description")
	val disbursementDescription: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)
