package com.example.vizzio.View

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.vizzio.R
import com.example.vizzio.databinding.ActivityDetailsBinding

class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val movieName = intent.getStringExtra(EXTRA_NAME) ?: "Unknown Movie"
        val movieRating = intent.getStringExtra(EXTRA_RATING) ?: "N/A"
        val movieSummary = intent.getStringExtra(EXTRA_OVERVIEW) ?: "No summary available."
        val posterUrl = intent.getStringExtra(EXTRA_POSTER_PATH) ?: ""
        val movieDuration = intent.getStringExtra(EXTRA_DURATION) ?: "N/A"


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
        }
    }

    companion object {
        const val EXTRA_NAME = "name"
        const val EXTRA_RATING = "rating"
        const val EXTRA_OVERVIEW = "overview"
        const val EXTRA_POSTER_PATH = "poster_path"
        const val EXTRA_DURATION = "duration"
    }
}
