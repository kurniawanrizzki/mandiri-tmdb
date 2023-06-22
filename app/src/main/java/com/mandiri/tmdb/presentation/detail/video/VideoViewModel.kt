package com.mandiri.tmdb.presentation.detail.video

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mandiri.tmdb.domain.common.ResultType
import com.mandiri.tmdb.domain.movie.entity.Video
import com.mandiri.tmdb.domain.movie.usecase.GetVideosUseCase
import com.mandiri.tmdb.presentation.detail.MOVIE_ID_ARG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class VideoViewModel @Inject constructor(
    getVideosUseCase: GetVideosUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _movieId: Int =
        savedStateHandle[MOVIE_ID_ARG] ?: 0

    val uiState = getVideosUseCase.invoke(_movieId)
        .map { result ->
            when (result) {
                is ResultType.Error -> VideoUiState(
                    loading = false,
                    message = result.e.localizedMessage
                )
                ResultType.Loading -> VideoUiState(
                    loading = true
                )
                is ResultType.Success -> VideoUiState(
                    loading = false,
                    data = result.data
                )
            }
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            VideoUiState(
                loading = true
            )
        )
}

data class VideoUiState(
    val loading: Boolean = false,
    val message: String? = null,
    val data: List<Video> = arrayListOf()
)