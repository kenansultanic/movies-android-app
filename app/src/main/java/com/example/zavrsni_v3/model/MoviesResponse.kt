package com.example.zavrsni_v3.model

data class MoviesResponse(
    val response: String,
    val Search: List<Movie>,
    val totalResults: String
)