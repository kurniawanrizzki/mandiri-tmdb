package com.mandiri.tmdb.presentation.detail.review

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.mandiri.tmdb.R
import com.mandiri.tmdb.databinding.FragmentReviewBinding
import com.mandiri.tmdb.presentation.common.AdaptiveSpacingItemDecoration
import com.mandiri.tmdb.presentation.common.ViewBindingFragment
import com.mandiri.tmdb.presentation.detail.MOVIE_ID_ARG
import com.mandiri.tmdb.presentation.detail.adapter.ReviewAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ReviewFragment : ViewBindingFragment<FragmentReviewBinding>() {
    private val viewModel by viewModels<ReviewViewModel>()

    private val adapter by lazy {
        ReviewAdapter()
    }
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentReviewBinding
        get() = FragmentReviewBinding::inflate

    override fun setup() = with(binding) {
        lifecycleOwner = viewLifecycleOwner

        observeUi()

        bindAdapter()
    }

    private fun observeUi() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.reviews.collectLatest {
                adapter.submitData(lifecycle, it)
            }
        }
    }

    private fun FragmentReviewBinding.bindAdapter() {
        rvReview.addItemDecoration(
            AdaptiveSpacingItemDecoration(
                resources.getDimension(R.dimen.normal_100).toInt(), true
            )
        )
        rvReview.adapter = adapter
    }

    companion object {
        fun newInstance(movieId: Int) =
            ReviewFragment().apply {
                this.arguments = Bundle().also {
                    it.putInt(MOVIE_ID_ARG, movieId)
                }
            }
    }
}