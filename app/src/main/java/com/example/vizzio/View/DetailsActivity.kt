package com.example.vizzio.View

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.vizzio.Model.FavoriteMovie
import com.example.vizzio.R
import com.example.vizzio.ViewModel.FavoriteViewModel
import com.example.vizzio.databinding.ActivityDetailsBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding
    private lateinit var favoriteViewModel: FavoriteViewModel
    private var movieId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        favoriteViewModel = ViewModelProvider(this)[FavoriteViewModel::class.java]

        val movieName = intent.getStringExtra(EXTRA_NAME) ?: "Unknown Movie"
        val movieRating = intent.getStringExtra(EXTRA_RATING) ?: "N/A"
        val movieSummary = intent.getStringExtra(EXTRA_OVERVIEW) ?: "No summary available."
        val posterUrl = intent.getStringExtra(EXTRA_POSTER_PATH) ?: ""
        val movieDuration = intent.getStringExtra(EXTRA_DURATION) ?: "N/A"
        movieId = intent.getIntExtra(EXTRA_MOVIE_ID, 0)

        Log.d("DetailsActivity", "Movie Name: $movieName")
        Log.d("DetailsActivity", "Movie Rating: $movieRating")
        Log.d("DetailsActivity", "Movie Summary: $movieSummary")
        Log.d("DetailsActivity", "Poster URL: $posterUrl")
        Log.d("DetailsActivity", "Movie Duration: $movieDuration")

        // Populate UI with movie details
        binding.apply {
            movieNameText.text = movieName
            movieRatingText.text = "Rating: $movieRating"
            movieSummaryText.text = movieSummary
            movieDurationText.text = "Duration: $movieDuration"

            if (posterUrl.isNotEmpty()) {
                Glide.with(this@DetailsActivity)
                    .load("https://image.tmdb.org/t/p/w500$posterUrl")
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .into(moviePosterImage)
            } else {
                moviePosterImage.setImageResource(R.drawable.ic_launcher_foreground)
            }

            // Set up favorite button
            favoriteButton.setOnClickListener {
                CoroutineScope(Dispatchers.Main).launch {
                    val isFavorite = favoriteViewModel.isMovieFavorite(movieId)
                    if (isFavorite) {
                        // Remove from favorites
                        val movie = FavoriteMovie(
                            id = movieId,
                            title = movieName,
                            posterPath = posterUrl,
                            overview = movieSummary,
                            rating = movieRating.toDoubleOrNull() ?: 0.0
                        )
                        favoriteViewModel.removeFromFavorites(movie)
                        favoriteButton.setImageResource(R.drawable.ic_heart_empty)
                        Toast.makeText(this@DetailsActivity, "Removed from favorites", Toast.LENGTH_SHORT).show()
                    } else {
                        // Add to favorites
                        val movie = FavoriteMovie(
                            id = movieId,
                            title = movieName,
                            posterPath = posterUrl,
                            overview = movieSummary,
                            rating = movieRating.toDoubleOrNull() ?: 0.0
                        )
                        favoriteViewModel.addToFavorites(movie)
                        favoriteButton.setImageResource(R.drawable.ic_heart_filled)
                        Toast.makeText(this@DetailsActivity, "Added to favorites", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            // Check initial favorite status
            CoroutineScope(Dispatchers.Main).launch {
                val isFavorite = favoriteViewModel.isMovieFavorite(movieId)
                favoriteButton.setImageResource(
                    if (isFavorite) R.drawable.ic_heart_filled
                    else R.drawable.ic_heart_empty
                )
            }
        }
    }

    companion object {
        const val EXTRA_NAME = "name"
        const val EXTRA_RATING = "rating"
        const val EXTRA_OVERVIEW = "overview"
        const val EXTRA_POSTER_PATH = "poster_path"
        const val EXTRA_DURATION = "duration"
        const val EXTRA_MOVIE_ID = "movie_id"
    }
}
