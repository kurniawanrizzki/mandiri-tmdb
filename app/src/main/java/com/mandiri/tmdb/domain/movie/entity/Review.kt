package com.mandiri.tmdb.domain.movie.entity

data class Review(
    val id: String,
    val author: String,
    val name: String,
    val avatarPath: String?,
    val rating: Float?,
    val content: String
) {
    val displayName: String =
        name.ifBlank { author }
}
