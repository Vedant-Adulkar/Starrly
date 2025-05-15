package com.example.vizzio.Model

data class Upcoming(
    val dates: Dates,
    val page: Int,
    val results: List<upcommingdata>,
    val total_pages: Int,
    val total_results: Int
)