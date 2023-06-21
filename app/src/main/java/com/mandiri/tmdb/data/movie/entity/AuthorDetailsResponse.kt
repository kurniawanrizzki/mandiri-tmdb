package com.mandiri.tmdb.data.movie.entity

import com.google.gson.annotations.SerializedName

data class AuthorDetailsResponse(
    val name: String,
    val username: String,
    @SerializedName("avatar_path") val avatarPath: String?,
    val rating: Float?
)
