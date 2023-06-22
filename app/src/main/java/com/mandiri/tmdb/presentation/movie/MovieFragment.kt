package com.mandiri.tmdb.presentation.movie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.mandiri.tmdb.R
import com.mandiri.tmdb.databinding.FragmentMovieBinding
import com.mandiri.tmdb.presentation.common.AdaptiveSpacingItemDecoration
import com.mandiri.tmdb.presentation.common.ViewBindingFragment
import com.mandiri.tmdb.presentation.movie.adapter.GenreAdapter
import com.mandiri.tmdb.presentation.movie.adapter.MovieAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieFragment : ViewBindingFragment<FragmentMovieBinding>() {
    private val viewModel by viewModels<MovieViewModel>()

    private val genreAdapter by lazy {
        GenreAdapter(viewModel)
    }

    private val movieAdapter by lazy {
        MovieAdapter { navigateToDetail(it) }
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentMovieBinding
        get() = FragmentMovieBinding::inflate

    override fun setup() = with(binding) {
        lifecycleOwner = viewLifecycleOwner

        observeUi()

        bindMovieAdapter()
        bindGenresAdapter()
    }

    private fun observeUi() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.collectLatest { state ->
                val (loading, message, data) = state

                binding.showHideEmpty(data.isEmpty())
                binding.showHideLoadingGenre(loading)

                genreAdapter.submitList(data)
                message?.let { showMessage(it) }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.movies.collectLatest {
                movieAdapter.submitData(lifecycle, it)

                if (viewModel.scrollToUp) {
                    binding.rvMovie.scrollToPosition(0)
                    viewModel.resetScrollToUp()
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            movieAdapter.loadStateFlow.collect { states ->
                val zeroItemCount = movieAdapter.itemCount == 0
                val loading = states.source.refresh is LoadState.Loading
                val empty = !loading && zeroItemCount

                val errorState = states.refresh as? LoadState.Error
                    ?: states.source.refresh as? LoadState.Error

                errorState?.let {
                    if (zeroItemCount)
                        showMessage(it.error.localizedMessage)
                }

                binding.showHideLoadingMovie(loading = loading)
                binding.showHideEmpty(empty = empty)
            }
        }
    }

    private fun FragmentMovieBinding.bindMovieAdapter() {
        val itemDecoration = AdaptiveSpacingItemDecoration(
            resources.getDimension(R.dimen.normal_100).toInt()
        )
        rvMovie.addItemDecoration(itemDecoration)
        rvMovie.adapter = movieAdapter
    }

    private fun FragmentMovieBinding.bindGenresAdapter() {
        val itemDecoration = AdaptiveSpacingItemDecoration(
            resources.getDimension(R.dimen.small_100).toInt(), true
        )
        rvGenre.addItemDecoration(itemDecoration)
        rvGenre.adapter = genreAdapter
    }

    private fun FragmentMovieBinding.showHideEmpty(empty: Boolean) {
        if (empty) {
            vEmpty.root.isVisible = true
            vContent.isGone = true
        } else {
            vEmpty.root.isGone = true
            vContent.isVisible = true
        }
    }

    private fun FragmentMovieBinding.showHideLoadingGenre(empty: Boolean) {
        if (empty) {
            vGenreLoading.isVisible = true
            rvGenre.isGone = true
        } else {
            vGenreLoading.isGone = true
            rvGenre.isVisible = true
        }
    }

    private fun FragmentMovieBinding.showHideLoadingMovie(loading: Boolean) {
        if (loading) {
            vMovieLoading.isVisible = true
            rvMovie.isGone = true
        } else {
            vMovieLoading.isGone = true
            rvMovie.isVisible = true
        }
    }

    private fun navigateToDetail(movieId: Int) {
        val action = MovieFragmentDirections.navigateToDetail(movieId)
        findNavController().navigate(action)
    }
}