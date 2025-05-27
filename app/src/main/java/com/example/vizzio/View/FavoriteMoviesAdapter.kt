package com.example.vizzio.View

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.vizzio.Model.FavoriteMovie
import com.example.vizzio.databinding.ItemFavoriteMovieBinding

class FavoriteMoviesAdapter(
    private val onMovieClick: (FavoriteMovie) -> Unit
) : ListAdapter<FavoriteMovie, FavoriteMoviesAdapter.ViewHolder>(FavoriteMovieDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemFavoriteMovieBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(
        private val binding: ItemFavoriteMovieBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onMovieClick(getItem(position))
                }
            }
        }

        fun bind(movie: FavoriteMovie) {
            binding.movieName.text = movie.title
            Glide.with(binding.root)
                .load("https://image.tmdb.org/t/p/w500${movie.posterPath}")
                .into(binding.moviePoster)
        }
    }

    private class FavoriteMovieDiffCallback : DiffUtil.ItemCallback<FavoriteMovie>() {
        override fun areItemsTheSame(oldItem: FavoriteMovie, newItem: FavoriteMovie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: FavoriteMovie, newItem: FavoriteMovie): Boolean {
            return oldItem == newItem
        }
    }
} 