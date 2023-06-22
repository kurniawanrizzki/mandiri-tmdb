package com.mandiri.tmdb.presentation.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.mandiri.tmdb.R
import com.mandiri.tmdb.databinding.ItemVideoBinding
import com.mandiri.tmdb.domain.movie.entity.Video

class VideoAdapter : ListAdapter<Video, VideoAdapter.VideoViewHolder>(comparator) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VideoViewHolder(
            ItemVideoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class VideoViewHolder(
        private val itemBinding: ItemVideoBinding
    ) : ViewHolder(itemBinding.root) {
        fun bind(item: Video) = with(itemBinding) {
            tvTitle.text = item.name
            ivVideo.load(item.thumbnail) {
                placeholder(R.drawable.bg_placeholder)
            }
        }
    }

    companion object {
        private val comparator = object : DiffUtil.ItemCallback<Video>() {
            override fun areItemsTheSame(oldItem: Video, newItem: Video): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Video, newItem: Video): Boolean =
                oldItem == newItem
        }
    }
}