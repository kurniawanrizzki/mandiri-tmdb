package com.mandiri.tmdb.data.movie.entity

import com.google.gson.annotations.SerializedName

data class ProductionCompanyResponse(
    val id: Int,
    @SerializedName("logo_path") val logoPath: String?,
    val name: String,
    @SerializedName("origin_country") val originCountry: String
)
