package com.mandiri.tmdb.presentation.movie

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.mandiri.tmdb.domain.common.ResultType
import com.mandiri.tmdb.domain.movie.usecase.GetGenresUseCase
import com.mandiri.tmdb.domain.movie.usecase.GetMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getMoviesUseCase: GetMoviesUseCase,
    getGenresUseCase: GetGenresUseCase
) : ViewModel() {
    private val _withGenresOperator = savedStateHandle.getStateFlow(
        GENRE_WITH_OPERATOR_SAVED_KEY, GetMoviesUseCase.OR_OPERATOR
    )

    private val _selectedGenres = savedStateHandle.getStateFlow(
        SELECTED_GENRE_IDS_SAVED_KEY, arrayListOf(RESET_ID)
    )

    var scrollToUp = false
        private set

    val uiState = getGenresUseCase.invoke()
        .map { result ->
            when (result) {
                is ResultType.Error -> GenresUiState(
                    loading = false,
                    message = result.e.localizedMessage
                )
                ResultType.Loading -> GenresUiState(
                    loading = true
                )
                is ResultType.Success -> GenresUiState(
                    loading = false,
                    genres = result.data.toMutableList().also {
                        it.add(
                            RESET_ID, RESET_ID to "All"
                        )
                    }
                )
            }
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            GenresUiState(
                loading = true
            )
        )

    @OptIn(ExperimentalCoroutinesApi::class)
    val movies = combine(
        _selectedGenres, _withGenresOperator
    ) { selectedGenres, withGenresOperator ->
        val filtered = if (selectedGenres.contains(RESET_ID)) emptyList()
        else selectedGenres
        withGenresOperator to filtered
    }.flatMapLatest {
        scrollToUp = true

        getMoviesUseCase.invoke(
            it.second,
            it.first
        ).cachedIn(viewModelScope)
    }

    fun updateGenres(genreId: Int = RESET_ID) {
        val selectedGenres = _selectedGenres.value.toMutableList().also {
            if (genreId > RESET_ID) {
                if (it.contains(genreId)) it.remove(genreId)
                else {
                    if (it.contains(RESET_ID))
                        it.remove(RESET_ID)
                    it.add(genreId)
                }
            } else {
                it.clear()
                it.add(RESET_ID)
            }
        }
        savedStateHandle[SELECTED_GENRE_IDS_SAVED_KEY] = selectedGenres
    }

    fun isFiltered(genreId: Int) =
        _selectedGenres.value.contains(genreId)

    fun resetScrollToUp() {
        scrollToUp = false
    }
}

private const val SELECTED_GENRE_IDS_SAVED_KEY = "selectedGenreIdsSavedKey"
private const val GENRE_WITH_OPERATOR_SAVED_KEY = "genreWithOperatorSavedKey"
private const val RESET_ID = 0

data class GenresUiState(
    val loading: Boolean = false,
    val message: String? = null,
    val genres: List<Pair<Int, String>> = emptyList()
)