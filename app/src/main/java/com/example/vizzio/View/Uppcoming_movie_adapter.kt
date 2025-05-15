package com.example.vizzio.View

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.vizzio.Model.upcommingdata
import com.example.vizzio.databinding.MovieslayoutBinding

class Uppcoming_movie_adapter(
    private val onMovieClick: (upcommingdata) -> Unit
) : RecyclerView.Adapter<Uppcoming_movie_adapter.ViewHolder>() {

    private val movieList = ArrayList<upcommingdata>()

    fun setMovieList(movies: List<upcommingdata>) {
        movieList.clear()
        movieList.addAll(movies)
        notifyDataSetChanged()
    }

    class ViewHolder(val binding: MovieslayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = MovieslayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = movieList[position]

        Glide.with(holder.itemView)
            .load("https://image.tmdb.org/t/p/w500${movie.poster_path}")
            .into(holder.binding.movieImage)

        holder.binding.movieName.text = movie.original_title

        holder.itemView.setOnClickListener {
            onMovieClick(movie)
        }
    }

    override fun getItemCount(): Int = movieList.size
}
