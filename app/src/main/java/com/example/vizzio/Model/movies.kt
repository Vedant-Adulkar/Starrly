package com.example.vizzio.Model

data class movies(
    val page: Int,
    val results: List<MoviesItem>,
    val total_pages: Int,
    val total_results: Int
)