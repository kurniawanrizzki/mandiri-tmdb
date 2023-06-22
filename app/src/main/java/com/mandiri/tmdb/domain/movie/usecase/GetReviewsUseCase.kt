package com.mandiri.tmdb.domain.movie.usecase

import com.mandiri.tmdb.domain.movie.MovieRepository
import javax.inject.Inject

class GetReviewsUseCase @Inject constructor(
    private val repo: MovieRepository
) {
    fun invoke(id: Int) =
        repo.getReviews(id)
}