package com.am.bbsa.data.response.report

import com.google.gson.annotations.SerializedName

data class NasabahWithdrawBalanceReportsResponse(

    @field:SerializedName("data")
    val data: List<DataItemNasabahWithdrawBalanceReports?>? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("status")
    val status: String? = null
)


data class DataItemNasabahWithdrawBalanceReports(

    @field:SerializedName("metode_penarikan")
    val metodePenarikan: String? = null,

    @field:SerializedName("failure_code")
    val failureCode: Any? = null,

    @field:SerializedName("jumlah_penarikan")
    val jumlahPenarikan: Int? = null,

    @field:SerializedName("nomor_rekening")
    val nomorRekening: String? = null,

    @field:SerializedName("created_at")
    val createdAt: String? = null,

    @field:SerializedName("external_id")
    val externalId: String? = null,

    @field:SerializedName("total_penarikan")
    val totalPenarikan: Int? = null,

    @field:SerializedName("nama_pemilik_akun")
    val namaPemilikAkun: String? = null,

    @field:SerializedName("updated_at")
    val updatedAt: String? = null,

    @field:SerializedName("user_id")
    val userId: Int? = null,

    @field:SerializedName("tanggal_penarikan_saldo")
    val tanggalPenarikanSaldo: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("user")
    val user: DataUserReport? = null,

    @field:SerializedName("status")
    val status: String? = null
)

data class DataUserReport(

    @field:SerializedName("saldo_aktual")
    val saldoAktual: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("nomor_register")
    val nomorRegister: String? = null
)




