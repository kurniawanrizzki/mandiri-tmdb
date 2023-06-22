package com.mandiri.tmdb.presentation.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mandiri.tmdb.domain.common.ResultType
import com.mandiri.tmdb.domain.movie.entity.MovieDetail
import com.mandiri.tmdb.domain.movie.usecase.GetMovieDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    getMovieDetailUseCase: GetMovieDetailUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _movieId: Int =
        savedStateHandle[MOVIE_ID_ARG] ?: 0

    val uiState = getMovieDetailUseCase.invoke(_movieId)
        .map { result ->
            when (result) {
                is ResultType.Error -> DetailUiState(
                    loading = false,
                    message = result.e.localizedMessage
                )
                ResultType.Loading -> DetailUiState(
                    loading = true
                )
                is ResultType.Success -> DetailUiState(
                    loading = false,
                    data = result.data
                )
            }
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            DetailUiState(
                loading = false
            )
        )
}

data class DetailUiState(
    val loading: Boolean = false,
    val message: String? = null,
    val data: MovieDetail? = null
)

const val MOVIE_ID_ARG = "movieIdArg"