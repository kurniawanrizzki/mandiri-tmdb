package com.mandiri.tmdb.domain.movie.usecase

import com.mandiri.tmdb.domain.movie.MovieRepository
import javax.inject.Inject

class GetMoviesUseCase @Inject constructor(
    private val repo: MovieRepository
) {
    fun invoke(
        genreIds: List<Int>,
        operator: CharSequence = OR_OPERATOR
    ) = repo.getMovies(genreIds, operator)

    companion object {
        const val AND_OPERATOR = ","
        const val OR_OPERATOR = "|"
    }
}