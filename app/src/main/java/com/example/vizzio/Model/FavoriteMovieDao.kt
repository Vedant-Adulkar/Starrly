package com.example.vizzio.Model

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FavoriteMovieDao {
    @Query("SELECT * FROM favorite_movies")
    fun getAllFavoriteMovies(): LiveData<List<FavoriteMovie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteMovie(movie: FavoriteMovie)

    @Delete
    suspend fun deleteFavoriteMovie(movie: FavoriteMovie)

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_movies WHERE id = :movieId)")
    suspend fun isMovieFavorite(movieId: Int): Boolean
} 