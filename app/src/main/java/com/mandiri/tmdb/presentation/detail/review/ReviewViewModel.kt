package com.mandiri.tmdb.presentation.detail.review

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.mandiri.tmdb.domain.movie.usecase.GetReviewsUseCase
import com.mandiri.tmdb.presentation.detail.MOVIE_ID_ARG
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ReviewViewModel @Inject constructor(
    getReviewsUseCase: GetReviewsUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _movieId: Int =
        savedStateHandle[MOVIE_ID_ARG] ?: 0

    val reviews =
        getReviewsUseCase.invoke(_movieId)
            .cachedIn(viewModelScope)
}