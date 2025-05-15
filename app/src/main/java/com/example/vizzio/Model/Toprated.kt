package com.example.vizzio.Model

data class Toprated(
    val page: Int,
    val results: List<topdata>,
    val total_pages: Int,
    val total_results: Int
)