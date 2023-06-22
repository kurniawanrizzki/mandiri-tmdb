package com.mandiri.tmdb.presentation.detail.video

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.mandiri.tmdb.R
import com.mandiri.tmdb.databinding.FragmentVideoBinding
import com.mandiri.tmdb.presentation.common.AdaptiveSpacingItemDecoration
import com.mandiri.tmdb.presentation.common.ViewBindingFragment
import com.mandiri.tmdb.presentation.detail.MOVIE_ID_ARG
import com.mandiri.tmdb.presentation.detail.adapter.VideoAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class VideoFragment : ViewBindingFragment<FragmentVideoBinding>() {
    private val viewModel by viewModels<VideoViewModel>()

    private val adapter: VideoAdapter by lazy {
        VideoAdapter()
    }
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentVideoBinding
        get() = FragmentVideoBinding::inflate

    override fun setup() = with(binding) {
        lifecycleOwner = viewLifecycleOwner

        observeUi()

        bindAdapter()
    }

    private fun observeUi() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.collectLatest { state ->
                val (loading, error, data) = state
                adapter.submitList(data)
            }
        }
    }

    private fun FragmentVideoBinding.bindAdapter() {
        rvVideo.addItemDecoration(
            AdaptiveSpacingItemDecoration(
                resources.getDimension(R.dimen.normal_100).toInt(),
                edgeEnabled = true
            )
        )
        rvVideo.adapter = adapter
    }
    
    companion object {
        fun newInstance(movieId: Int) =
            VideoFragment().apply {
                this.arguments = Bundle().also {
                    it.putInt(MOVIE_ID_ARG, movieId)
                }
            }
    }
}