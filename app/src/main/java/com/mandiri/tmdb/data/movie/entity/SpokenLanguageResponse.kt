package com.mandiri.tmdb.data.movie.entity

import com.google.gson.annotations.SerializedName

data class SpokenLanguageResponse(
    @SerializedName("english_name") val englishName: String,
    @SerializedName("iso_639_1") val iso6391: String,
    val name: String
)
