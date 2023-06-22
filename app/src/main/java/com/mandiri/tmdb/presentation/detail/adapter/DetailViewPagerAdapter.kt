package com.mandiri.tmdb.presentation.detail.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mandiri.tmdb.presentation.detail.about.AboutFragment
import com.mandiri.tmdb.presentation.detail.review.ReviewFragment
import com.mandiri.tmdb.presentation.detail.video.VideoFragment

class DetailViewPagerAdapter(
    private val movieId: Int,
    fragment: Fragment
) : FragmentStateAdapter(fragment) {
    private val tabs = mapOf(
        ABOUT_INDEX to { AboutFragment.newInstance(movieId) },
        REVIEW_INDEX to { ReviewFragment.newInstance(movieId) },
        VIDEO_INDEX to { VideoFragment.newInstance(movieId) },
    )

    override fun getItemCount(): Int =
        tabs.size

    override fun createFragment(position: Int): Fragment =
        tabs[position]?.invoke() ?: throw IllegalArgumentException("Unidentified Tab")
}

const val ABOUT_INDEX = 0
const val REVIEW_INDEX = 1
const val VIDEO_INDEX = 2