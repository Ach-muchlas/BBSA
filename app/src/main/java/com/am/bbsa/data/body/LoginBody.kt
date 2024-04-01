package com.am.bbsa.data.body

import com.google.gson.annotations.SerializedName

data class LoginBody(
    @field:SerializedName("nomor_telephone") val phoneNumber: String,
    @field:SerializedName("password") val password: String,
)