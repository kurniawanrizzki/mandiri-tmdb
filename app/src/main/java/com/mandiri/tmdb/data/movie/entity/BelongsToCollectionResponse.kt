package com.mandiri.tmdb.data.movie.entity

import com.google.gson.annotations.SerializedName

data class BelongsToCollectionResponse(
    val id: Int,
    val name: String,
    @SerializedName("poster_path") val posterPath: String,
    @SerializedName("backdrop_path") val backdropPath: String
)
