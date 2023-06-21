package com.mandiri.tmdb.domain.movie.usecase

import com.mandiri.tmdb.domain.movie.MovieRepository

class GetMoviesUseCase constructor(
    private val repo: MovieRepository
) {
    fun invoke(
        genreIds: List<Int>,
        operator: CharSequence
    ) = repo.getMovies(genreIds, operator)
}