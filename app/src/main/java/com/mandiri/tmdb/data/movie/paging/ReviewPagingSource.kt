package com.mandiri.tmdb.data.movie.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mandiri.tmdb.data.common.DEFAULT_PAGE_NUMBER
import com.mandiri.tmdb.data.common.ErrorHelper
import com.mandiri.tmdb.data.movie.api.MovieApi
import com.mandiri.tmdb.data.movie.entity.toReviews
import com.mandiri.tmdb.domain.movie.entity.Review
import retrofit2.HttpException
import java.io.IOException

class ReviewPagingSource(
    private val id: Int,
    private val api: MovieApi
) : PagingSource<Int, Review>() {
    override fun getRefreshKey(state: PagingState<Int, Review>) =
        state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.inc()
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.dec()
        }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Review> {
        val page = params.key ?: DEFAULT_PAGE_NUMBER
        return try {
            val response = api.getReviews(id)
            if (response.isSuccessful) {
                val body = response.body()
                val totalPages = body?.results ?: DEFAULT_PAGE_NUMBER
                val data = body?.results ?: arrayListOf()
                LoadResult.Page(
                    data = data.toReviews(),
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