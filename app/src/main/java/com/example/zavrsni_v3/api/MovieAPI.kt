package com.example.zavrsni_v3.api

import com.example.zavrsni_v3.model.Movie
import com.example.zavrsni_v3.model.MovieDetails
import com.example.zavrsni_v3.model.MoviesResponse
import com.example.zavrsni_v3.util.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieAPI {

    @GET("/")
    suspend fun getMovies(
        @Query("s")
        search: String,
        @Query("type")
        type: String = "",
        @Query("page")
        page: Int = 1,
        @Query("apikey")
        apiKey: String = Constants.API_KEY
    ): Response<MoviesResponse>

    @GET("/")
    suspend fun getMovie(
        @Query("i")
        imbdID: String,
        @Query("apikey")
        apiKey: String = Constants.API_KEY
    ): Response<MovieDetails>
}