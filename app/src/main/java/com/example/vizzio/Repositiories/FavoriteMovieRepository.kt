package com.example.vizzio.Repositiories

import androidx.lifecycle.LiveData
import com.example.vizzio.Model.AppDatabase
import com.example.vizzio.Model.FavoriteMovie

class FavoriteMovieRepository(private val database: AppDatabase) {
    
    val allFavoriteMovies: LiveData<List<FavoriteMovie>> = database.favoriteMovieDao().getAllFavoriteMovies()

    suspend fun insertFavoriteMovie(movie: FavoriteMovie) {
        database.favoriteMovieDao().insertFavoriteMovie(movie)
    }

    suspend fun deleteFavoriteMovie(movie: FavoriteMovie) {
        database.favoriteMovieDao().deleteFavoriteMovie(movie)
    }

    suspend fun isMovieFavorite(movieId: Int): Boolean {
        return database.favoriteMovieDao().isMovieFavorite(movieId)
    }
} 