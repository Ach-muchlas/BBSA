package com.am.bbsa.data.body

import com.google.gson.annotations.SerializedName

data class RegisterBody(
    @field:SerializedName("name") val name: String,
    @field:SerializedName("nomor_telephone") val phoneNumber: String,
    @field:SerializedName("jenis_kelamin") val gender: String,
    @field:SerializedName("alamat") val address: String,
    @field:SerializedName("password") val password: String,
    @field:SerializedName("NIK") val NIK: String,
)

data class CreateAdminBody(
    @field:SerializedName("name") val name: String,
    @field:SerializedName("nomor_telephone") val phoneNumber: String,
    @field:SerializedName("jenis_kelamin") val gender: String,
    @field:SerializedName("alamat") val address: String,
    @field:SerializedName("password") val password: String,
)