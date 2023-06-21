package com.mandiri.tmdb.domain.movie

import androidx.paging.PagingData
import com.mandiri.tmdb.domain.common.ResultType
import com.mandiri.tmdb.domain.movie.entity.Movie
import com.mandiri.tmdb.domain.movie.entity.MovieDetail
import com.mandiri.tmdb.domain.movie.entity.Review
import com.mandiri.tmdb.domain.movie.entity.Video
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getMovies(
        genreIds: List<Int>,
        operator: CharSequence
    ): Flow<PagingData<Movie>>

    fun getMovieById(
        id: Int
    ): Flow<ResultType<MovieDetail>>

    fun getVideos(
        id: Int
    ): Flow<ResultType<List<Video>>>

    fun getReviews(
        id: Int
    ): Flow<PagingData<Review>>

    fun getGenres(): Flow<ResultType<List<Pair<Int, String>>>>
}