package com.mandiri.tmdb.data.common

import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName("status_code") val statusCode: Int,
    @SerializedName("status_message") val statusMessage: String,
    val success: Boolean
)
