package com.mandiri.tmdb.presentation.detail.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.mandiri.tmdb.databinding.FragmentAboutBinding
import com.mandiri.tmdb.presentation.common.ViewBindingFragment
import com.mandiri.tmdb.presentation.detail.DetailViewModel
import com.mandiri.tmdb.presentation.detail.MOVIE_ID_ARG
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AboutFragment : ViewBindingFragment<FragmentAboutBinding>() {
    private val viewModel by viewModels<DetailViewModel>()
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentAboutBinding
        get() = FragmentAboutBinding::inflate

    override fun setup() = with(binding) {
        lifecycleOwner = viewLifecycleOwner

        observeUi()
    }

    private fun observeUi() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.collectLatest { state ->
                val (loading, error, data) = state
                data?.let { binding.bindOverview(it.overview) }
            }
        }
    }

    private fun FragmentAboutBinding.bindOverview(overview: String) {
        tvAbout.text = overview
    }

    companion object {
        fun newInstance(movieId: Int) =
            AboutFragment().apply {
                this.arguments = Bundle().also {
                    it.putInt(MOVIE_ID_ARG, movieId)
                }
            }
    }
}