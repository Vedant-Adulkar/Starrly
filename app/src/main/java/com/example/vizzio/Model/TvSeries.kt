package com.example.vizzio.Model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TvSeries {
    @GET("popular?")
    fun getPopularTvSeries(@Query("api_key") apiKey: String): Call<TvSeriesPop>
}