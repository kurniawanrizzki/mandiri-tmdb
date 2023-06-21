package com.mandiri.tmdb.data.movie.entity

import com.google.gson.annotations.SerializedName

data class PaginatedResponse<T>(
    val page: Int,
    val results: List<T>,
    @SerializedName("total_pages") val totalPages: Int?,
    @SerializedName("total_results") val totalResults: Int?
)
