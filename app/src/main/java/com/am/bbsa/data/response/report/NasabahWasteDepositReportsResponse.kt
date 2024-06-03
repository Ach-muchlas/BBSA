package com.am.bbsa.data.response.report

import com.google.gson.annotations.SerializedName

data class NasabahWasteDepositReportsResponse(

	@field:SerializedName("data")
	val data: List<DataItemNasabahWasteDepositReport?>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class DataItemNasabahWasteDepositReport(

	@field:SerializedName("total_setoran")
	val totalSetoran: Int? = null,

	@field:SerializedName("tanggal_setor")
	val tanggalSetor: String? = null,

	@field:SerializedName("sampah")
	val sampah: List<SampahItem?>? = null
)

data class SampahItem(

	@field:SerializedName("nama")
	val nama: String? = null,

	@field:SerializedName("berat_sampah")
	val beratSampah: Int? = null
)
