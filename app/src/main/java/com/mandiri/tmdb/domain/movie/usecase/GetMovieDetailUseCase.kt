package com.mandiri.tmdb.domain.movie.usecase

import com.mandiri.tmdb.domain.movie.MovieRepository

class GetMovieDetailUseCase constructor(
    private val repo: MovieRepository
) {
    fun invoke(id: Int) =
        repo.getMovieById(id)
}