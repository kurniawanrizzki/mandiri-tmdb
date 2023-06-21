package com.mandiri.tmdb.domain.movie.usecase

import com.mandiri.tmdb.domain.movie.MovieRepository

class GetGenresUseCase constructor(
    private val repo: MovieRepository
) {
    fun invoke() = repo.getGenres()
}