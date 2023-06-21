package com.mandiri.tmdb.data.movie.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mandiri.tmdb.data.common.DEFAULT_PAGE_NUMBER
import com.mandiri.tmdb.data.common.ErrorHelper
import com.mandiri.tmdb.data.movie.api.MovieApi
import com.mandiri.tmdb.data.movie.entity.toMovies
import com.mandiri.tmdb.domain.movie.entity.Movie
import retrofit2.HttpException
import java.io.IOException

class MoviePagingSource(
    private val genreIds: List<Int>,
    private val operator: CharSequence,
    private val api: MovieApi
) : PagingSource<Int, Movie>() {
    override fun getRefreshKey(state: PagingState<Int, Movie>) =
        state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.inc()
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.dec()
        }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val page = params.key ?: DEFAULT_PAGE_NUMBER
        return try {
            val withGenres = genreIds.joinToString(operator)
            val response = api.getMovies(
                withGenres = withGenres,
                page = page
            )
            if (response.isSuccessful) {
                val body = response.body()
                val totalPages = body?.totalPages ?: DEFAULT_PAGE_NUMBER
                val data = body?.results ?: arrayListOf()
                LoadResult.Page(
                    data = data.toMovies(),
                    prevKey = if (page == DEFAULT_PAGE_NUMBER) null else page.dec(),
                    nextKey = if ((page == totalPages)) null else page.inc()
                )
            } else {
                val e = ErrorHelper.resolve(response)
                LoadResult.Error(e)
            }
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }
}