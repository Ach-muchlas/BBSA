package com.am.bbsa.data.body

import com.google.gson.annotations.SerializedName

data class ChangePasswordBody(
    @field:SerializedName("password_lama") val oldPassword: String,
    @field:SerializedName("password_baru") val newPassword: String,
    @field:SerializedName("ulangi_password_baru") val repeatNewPassword: String,
)
