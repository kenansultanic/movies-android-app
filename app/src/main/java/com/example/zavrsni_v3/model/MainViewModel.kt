package com.example.zavrsni_v3.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zavrsni_v3.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel(private val repository: Repository): ViewModel() {

    val myResponse: MutableLiveData<Response<Movie?>> = MutableLiveData()
    val responseMoviesList: MutableLiveData<Response<MoviesResponse>> = MutableLiveData()
    val responseMovieDetails: MutableLiveData<Response<MovieDetails>> = MutableLiveData()
    val allSavedMovies: LiveData<List<MovieDetails>> = repository.moviesDao.getAllMovies()
    var allSavedMoviesFiltered: LiveData<List<MovieDetails>> = MutableLiveData()
    val moviesCache: LiveData<List<Movie>> = repository.moviesDao.getAllMoviesCache()
    val movie: MutableLiveData<MovieDetails> = MutableLiveData()

    fun getMovies(searchQuery: String, type: String) {
        viewModelScope.launch {
            val response = repository.getMovies(searchQuery, type)
            responseMoviesList.value = response
        }
    }

    fun getMovie(imdbID: String) {
        viewModelScope.launch {
            val response = repository.getMovie(imdbID)
            responseMovieDetails.value = response
        }
    }

    fun addMovieToWatchLater(movie: MovieDetails) {
        viewModelScope.launch {
            repository.addMovieToWatchLater(movie)
        }
    }

    fun getMovieFromDatabase(imdbID: String) {
        viewModelScope.launch {
            val response = repository.getMovieFromDatabase(imdbID)
            movie.value = response
        }
    }

    fun deleteMovieFromDatabase(movie: MovieDetails) {
        viewModelScope.launch {
            repository.deleteMovieFromDatabase(movie)
        }
    }

    fun getMoviesFiltered(genre: String, year: String,  sortBy: String) {
        var genre_ = genre
        var year_ = year
        if (genre.equals("Any")) genre_ = ""
        if(year.equals("Any")) year_ = ""
        viewModelScope.launch {
            val response = repository.moviesDao.getMoviesFiltered(genre_, year_, sortBy)
            allSavedMoviesFiltered= response
        }
    }

    fun addMoviesToCache(movie: Movie) {
        viewModelScope.launch {
            repository.addMoviesToCache(movie)
        }
    }

    fun deleteCache() {
        viewModelScope.launch {
            repository.deleteCache()
        }
    }
}