package com.example.zavrsni_v3.util

import com.example.zavrsni_v3.model.Movie
import com.example.zavrsni_v3.model.MovieDetails

class Parse {
    companion object {
        fun parseMovies(list: List<MovieDetails>): List<Movie> {
            val parsed = ArrayList<Movie>()
            list.map { movieDetails ->
                parsed.add(Movie(movieDetails.imdbID, movieDetails.Poster, movieDetails.Title, movieDetails.Type, movieDetails.Year))
            }
            return parsed
        }
    }
}