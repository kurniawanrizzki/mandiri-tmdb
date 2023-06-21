package com.mandiri.tmdb.data.movie.entity

import com.google.gson.annotations.SerializedName
import java.util.Date

data class ReviewResponse(
    val id: String,
    val url: String,
    val author: String,
    @SerializedName("author_details") val authorDetails: AuthorDetailsResponse,
    val content: String,
    @SerializedName("created_at") val createdAt: Date,
    @SerializedName("updated_at") val updatedAt: Date
)
