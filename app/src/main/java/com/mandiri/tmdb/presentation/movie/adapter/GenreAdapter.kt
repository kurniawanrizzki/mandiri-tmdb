package com.mandiri.tmdb.presentation.movie.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.mandiri.tmdb.databinding.ItemGenreBinding
import com.mandiri.tmdb.presentation.movie.MovieViewModel

class GenreAdapter(
    private val viewModel: MovieViewModel
) : ListAdapter<Pair<Int, String>, GenreAdapter.GenreViewHolder>(comparator) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        GenreViewHolder(
            ItemGenreBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        holder.itemView.isSelected = viewModel.isFiltered(item.first)
    }

    inner class GenreViewHolder(
        private val itemBinding: ItemGenreBinding
    ) : ViewHolder(itemBinding.root) {
        @SuppressLint("NotifyDataSetChanged")
        fun bind(item: Pair<Int, String>) = with(itemBinding) {
            val (id, text) = item
            this.text = text
            this.callback = View.OnClickListener {
                viewModel.updateGenres(id)
                notifyDataSetChanged()
            }
        }
    }

    companion object {
        private val comparator = object : DiffUtil.ItemCallback<Pair<Int, String>>() {
            override fun areItemsTheSame(
                oldItem: Pair<Int, String>,
                newItem: Pair<Int, String>
            ): Boolean =
                oldItem.first == newItem.first

            override fun areContentsTheSame(
                oldItem: Pair<Int, String>,
                newItem: Pair<Int, String>
            ): Boolean =
                oldItem == newItem
        }
    }
}