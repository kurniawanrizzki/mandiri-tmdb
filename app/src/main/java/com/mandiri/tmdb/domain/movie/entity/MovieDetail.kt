package com.mandiri.tmdb.domain.movie.entity

data class MovieDetail(
    val movie: Movie,
    val originalLanguage: String,
    val backdropPath: String,
    val genres: String,
    val tagline: String?,
    val overview: String,
    val status: String?,
    val revenue: Float?
)
