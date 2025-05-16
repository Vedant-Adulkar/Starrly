package com.example.vizzio.Model

data class TvSeriesPop(
    val page: Int,
    val results: List<popdata>,
    val total_pages: Int,
    val total_results: Int
)