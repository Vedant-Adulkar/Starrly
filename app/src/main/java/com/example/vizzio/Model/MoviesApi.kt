package com.example.vizzio.Model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApi {

    @GET("popular?")
    fun getPopularMovies(@Query("api_key") api_key: String): Call<movies>

    @GET("upcoming?")
    fun getUpcomingMovies(@Query("api_key") api_key: String): Call<Upcoming>
}
