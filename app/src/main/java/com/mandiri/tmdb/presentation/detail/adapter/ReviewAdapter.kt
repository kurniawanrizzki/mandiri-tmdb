package com.mandiri.tmdb.presentation.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.mandiri.tmdb.R
import com.mandiri.tmdb.databinding.ItemReviewBinding
import com.mandiri.tmdb.domain.movie.entity.Review

class ReviewAdapter : PagingDataAdapter<Review, ReviewAdapter.ReviewViewHolder>(comparator) {
    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ReviewViewHolder(
            ItemReviewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    class ReviewViewHolder(
        private val itemBinding: ItemReviewBinding
    ) : ViewHolder(itemBinding.root) {
        fun bind(item: Review?) = with(itemBinding) {
            ivAvatar.load(item?.avatarPath) {
                placeholder(R.drawable.bg_placeholder)
                error(R.drawable.ic_default_avatar)
            }
            tvUser.text = item?.displayName
            tvRate.text = "${item?.rating ?: "-"}"
            tvReview.text = item?.content
        }
    }

    companion object {
        private val comparator = object : DiffUtil.ItemCallback<Review>() {
            override fun areItemsTheSame(oldItem: Review, newItem: Review) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Review, newItem: Review) =
                oldItem == newItem
        }
    }
}