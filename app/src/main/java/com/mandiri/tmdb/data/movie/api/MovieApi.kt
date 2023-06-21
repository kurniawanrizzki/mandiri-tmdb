package com.mandiri.tmdb.data.movie.api

import com.mandiri.tmdb.data.movie.entity.GenresResponse
import com.mandiri.tmdb.data.movie.entity.MovieResponse
import com.mandiri.tmdb.data.movie.entity.PaginatedResponse
import com.mandiri.tmdb.data.movie.entity.ReviewResponse
import com.mandiri.tmdb.data.movie.entity.VideoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {
    @GET("discover/movie")
    suspend fun getMovies(
        @Query("with_genres") withGenres: String,
        @Query("page") page: Int
    ): Response<PaginatedResponse<MovieResponse>>

    @GET("movie/{id}")
    suspend fun getMovieById(
        @Path("id") id: Int
    ): Response<MovieResponse>

    @GET("movie/{id}/videos")
    suspend fun getVideos(
        @Path("id") id: Int
    ): Response<PaginatedResponse<VideoResponse>>

    @GET("movie/{id}/reviews")
    suspend fun getReviews(
        @Path("id") id: Int
    ): Response<PaginatedResponse<ReviewResponse>>

    @GET("genre/movie/list")
    suspend fun getGenres(): Response<GenresResponse>
}