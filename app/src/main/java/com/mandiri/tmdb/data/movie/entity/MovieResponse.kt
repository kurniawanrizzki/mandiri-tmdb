package com.mandiri.tmdb.data.movie.entity

import com.google.gson.annotations.SerializedName
import com.mandiri.tmdb.data.common.PairResponse

data class MovieResponse(
    val id: Int,
    @SerializedName("imdb_id") val imdbId: String?,
    val title: String,
    @SerializedName("original_title") val originalTitle: String,
    @SerializedName("original_language") val originalLanguage: String,
    @SerializedName("poster_path") val posterPath: String,
    @SerializedName("backdrop_path") val backdropPath: String,
    @SerializedName("release_date") val releaseDate: String,
    @SerializedName("belongs_to_collection") val belongsToCollection: BelongsToCollectionResponse?,
    @SerializedName("production_companies") val productionCompanies: List<ProductionCompanyResponse>?,
    @SerializedName("production_countries") val productionCountries: List<ProductionCountryResponse>?,
    @SerializedName("spoken_languages") val spokenLanguages: List<SpokenLanguageResponse>?,
    val overview: String,
    val homepage: String?,
    val adult: Boolean,
    val video: Boolean,
    val budget: Float?,
    val revenue: Float?,
    val runtime: Int?,
    val status: String?,
    val tagline: String?,
    @SerializedName("genre_ids") val genreIds: List<Int>?,
    val genres: List<PairResponse>?,
    val popularity: Float,
    @SerializedName("vote_average") val voteAverage: Float,
    @SerializedName("vote_count") val voteCount: Int,
)
