package com.mandiri.tmdb.presentation.movie.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.mandiri.tmdb.R
import com.mandiri.tmdb.databinding.ItemMovieBinding
import com.mandiri.tmdb.domain.movie.entity.Movie

class MovieAdapter(
    val callback: (movieId: Int) -> Unit
) : PagingDataAdapter<Movie, MovieAdapter.MovieViewHolder>(comparator) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        MovieViewHolder(
            ItemMovieBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        holder.itemView.setOnClickListener {
            item ?: return@setOnClickListener
            callback.invoke(item.id)
        }
    }

    class MovieViewHolder(
        private val itemBinding: ItemMovieBinding
    ) : ViewHolder(itemBinding.root) {
        fun bind(item: Movie?) = with(itemBinding) {
            ivMovie.load(item?.posterPath) {
                placeholder(R.drawable.bg_placeholder)
            }
        }
    }

    companion object {
        private val comparator = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem == newItem
        }
    }
}
