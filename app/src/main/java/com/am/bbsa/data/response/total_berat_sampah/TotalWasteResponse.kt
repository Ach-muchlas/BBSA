package com.am.bbsa.data.response.total_berat_sampah

import com.google.gson.annotations.SerializedName

data class TotalWasteResponse(

    @field:SerializedName("data")
	val data: DataItemTotalWaste? = null,

    @field:SerializedName("message")
	val message: String? = null,

    @field:SerializedName("status")
	val status: Boolean? = null
)

data class JenisSampahInfoItem(

	@field:SerializedName("total_jenis_berat_sampah")
	val totalJenisBeratSampah: Int? = null,

	@field:SerializedName("jenis_sampah")
	val jenisSampah: String? = null
)

data class DataItemTotalWaste(

    @field:SerializedName("jenis_sampah_info")
	val jenisSampahInfo: List<JenisSampahInfoItem?>? = null,

    @field:SerializedName("total_berat_semua_sampah")
	val totalBeratSemuaSampah: Int? = null
)
