package com.am.bbsa.data.response

import com.google.gson.annotations.SerializedName

data class GeneralResponse(

    @field:SerializedName("data")
    val data: List<Any?>? = null,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("status")
    val status: Boolean? = null
)
