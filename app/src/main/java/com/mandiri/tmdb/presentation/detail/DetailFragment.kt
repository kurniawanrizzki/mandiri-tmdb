package com.mandiri.tmdb.presentation.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import coil.load
import com.google.android.material.tabs.TabLayoutMediator
import com.mandiri.tmdb.R
import com.mandiri.tmdb.databinding.FragmentDetailBinding
import com.mandiri.tmdb.domain.movie.entity.MovieDetail
import com.mandiri.tmdb.presentation.common.ViewBindingFragment
import com.mandiri.tmdb.presentation.detail.adapter.ABOUT_INDEX
import com.mandiri.tmdb.presentation.detail.adapter.DetailViewPagerAdapter
import com.mandiri.tmdb.presentation.detail.adapter.REVIEW_INDEX
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailFragment : ViewBindingFragment<FragmentDetailBinding>() {
    private val viewModel by viewModels<DetailViewModel>()

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentDetailBinding
        get() = FragmentDetailBinding::inflate

    override fun setup() = with(binding) {
        lifecycleOwner = viewLifecycleOwner

        observeUi()

    }

    private fun observeUi() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.collectLatest { state ->
                val (_, _, data) = state
                data?.let { bindToUi(it) }
            }
        }
    }

    private fun bindToUi(data: MovieDetail) = with(binding) {
        val (id, title) = data.movie

        viewpager.adapter = DetailViewPagerAdapter(
            movieId = id,
            fragment = this@DetailFragment
        )
        bindTab()
        ivBackdrop.load(data.backdropPath) {
            placeholder(R.drawable.bg_placeholder)
        }
        ivPoster.load(data.movie.posterPath) {
            placeholder(R.drawable.bg_placeholder)
        }
        tvTitle.text = title
        tvDuration.text = getString(R.string.duration, data.runtime)
        tvGenres.text = data.genres
        tvReleaseYear.text = data.year
    }

    private fun FragmentDetailBinding.bindTab() {
        viewpager.isUserInputEnabled = false
        TabLayoutMediator(tab, viewpager) { t, position ->
            t.text = getTitle(position)
        }.attach()
    }

    private fun getTitle(position: Int) =
        getString(
            when (position) {
                ABOUT_INDEX -> R.string.about
                REVIEW_INDEX -> R.string.review
                else -> R.string.video
            }
        )
}