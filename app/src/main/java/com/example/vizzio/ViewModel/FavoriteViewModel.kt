package com.example.vizzio.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.vizzio.Model.AppDatabase
import com.example.vizzio.Model.FavoriteMovie
import com.example.vizzio.Repositiories.FavoriteMovieRepository
import kotlinx.coroutines.launch

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: FavoriteMovieRepository
    val allFavoriteMovies: LiveData<List<FavoriteMovie>>

    init {
        val database = AppDatabase.getDatabase(application)
        repository = FavoriteMovieRepository(database)
        allFavoriteMovies = repository.allFavoriteMovies
    }

    fun addToFavorites(movie: FavoriteMovie) {
        viewModelScope.launch {
            repository.insertFavoriteMovie(movie)
        }
    }

    fun removeFromFavorites(movie: FavoriteMovie) {
        viewModelScope.launch {
            repository.deleteFavoriteMovie(movie)
        }
    }

    suspend fun isMovieFavorite(movieId: Int): Boolean {
        return repository.isMovieFavorite(movieId)
    }
} 