package com.example.vizzio.Model

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object TvInstance {
    val api : TvSeries by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/tv/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TvSeries::class.java)
    }
}