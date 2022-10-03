package com.example.zavrsni_v3.repository

import android.content.Context
import com.example.zavrsni_v3.api.RetrofitInstance
import com.example.zavrsni_v3.database.MoviesDao
import com.example.zavrsni_v3.database.MoviesDatabase
import com.example.zavrsni_v3.model.Movie
import com.example.zavrsni_v3.model.MovieDetails
import com.example.zavrsni_v3.model.MoviesResponse
import retrofit2.Response

class Repository(private val context: Context) {

    val moviesDao: MoviesDao = MoviesDatabase.getDatabase(context).moviesDao()

    suspend fun getMovies(searchQuery: String, type: String): Response<MoviesResponse> {
        return RetrofitInstance.api.getMovies(searchQuery, type)
    }

    suspend fun getMovie(imdbID: String): Response<MovieDetails> {
        return RetrofitInstance.api.getMovie(imdbID)
    }

    suspend fun addMovieToWatchLater(movie: MovieDetails) {
        moviesDao.addMovie(movie)
    }

    suspend fun getMovieFromDatabase(imdbID: String): MovieDetails {
        return moviesDao.getMovie(imdbID)
    }

    suspend fun deleteMovieFromDatabase(movie: MovieDetails) {
        moviesDao.deleteMovie(movie)
    }

    suspend fun addMoviesToCache(movie: Movie) {
        moviesDao.addMoviesToCache(movie)
    }

    suspend fun deleteCache() {
        moviesDao.deleteCache()
    }
}