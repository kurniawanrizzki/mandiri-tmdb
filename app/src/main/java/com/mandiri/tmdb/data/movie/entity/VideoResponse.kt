package com.mandiri.tmdb.data.movie.entity

import com.google.gson.annotations.SerializedName
import java.util.Date

data class VideoResponse(
    val id: String,
    @SerializedName("iso_639_1") val iso6391: String,
    @SerializedName("iso_3166_1") val iso31661: String,
    val name: String,
    val key: String,
    val site: String,
    val size: Int,
    val type: String,
    val official: Boolean,
    @SerializedName("published_at") val publishedAt: Date
)
