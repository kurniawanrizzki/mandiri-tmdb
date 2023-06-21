package com.mandiri.tmdb.data.movie.entity

import com.google.gson.annotations.SerializedName

data class ProductionCountryResponse(
    @SerializedName("iso_3166_1") val iso31661: String,
    val name: String
)
