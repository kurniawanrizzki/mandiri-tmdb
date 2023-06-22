package com.mandiri.tmdb.data.movie

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.mandiri.tmdb.data.common.DEFAULT_PAGE_SIZE
import com.mandiri.tmdb.data.common.ErrorHelper
import com.mandiri.tmdb.data.movie.api.MovieApi
import com.mandiri.tmdb.data.movie.entity.toMovieDetail
import com.mandiri.tmdb.data.movie.entity.toPairs
import com.mandiri.tmdb.data.movie.entity.toVideos
import com.mandiri.tmdb.data.movie.paging.MoviePagingSource
import com.mandiri.tmdb.data.movie.paging.ReviewPagingSource
import com.mandiri.tmdb.domain.common.ResultType
import com.mandiri.tmdb.domain.movie.MovieRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val api: MovieApi
) : MovieRepository {
    override fun getMovies(
        genreIds: List<Int>,
        operator: CharSequence
    ) = Pager(
        config = PagingConfig(
            pageSize = DEFAULT_PAGE_SIZE,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { MoviePagingSource(genreIds, operator, api) }
    ).flow

    override fun getMovieById(id: Int) = flow {
        emit(ResultType.Loading)

        val response = api.getMovieById(id)
        if (response.isSuccessful) {
            val body = response.body()!!
            emit(ResultType.Success(body.toMovieDetail()))
        } else {
            val e = ErrorHelper.resolve(response)
            emit(ResultType.Error(e))
        }
    }.catch {
        emit(ResultType.Error(it))
    }

    override fun getVideos(id: Int) = flow {
        emit(ResultType.Loading)

        val response = api.getVideos(id)
        if (response.isSuccessful) {
            val body = response.body()
            val data = body?.results ?: arrayListOf()
            emit(ResultType.Success(data.toVideos()))
        } else {
            val e = ErrorHelper.resolve(response)
            emit(ResultType.Error(e))
        }
    }.catch {
        emit(ResultType.Error(it))
    }

    override fun getReviews(id: Int) =
        Pager(
            config = PagingConfig(
                pageSize = DEFAULT_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { ReviewPagingSource(id, api) }
        ).flow

    override fun getGenres() = flow {
        emit(ResultType.Loading)

        val response = api.getGenres()
        if (response.isSuccessful) {
            val body = response.body()!!
            emit(ResultType.Success(body.genres.toPairs()))
        } else {
            val e = ErrorHelper.resolve(response)
            emit(ResultType.Error(e))
        }
    }.catch {
        emit(ResultType.Error(it))
    }
}