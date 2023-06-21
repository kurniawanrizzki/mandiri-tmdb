package com.mandiri.tmdb.domain.movie.usecase

import com.mandiri.tmdb.domain.movie.MovieRepository

class GetReviewsUseCase constructor(
    private val repo: MovieRepository
) {
    fun invoke(id: Int) =
        repo.getReviews(id)
}