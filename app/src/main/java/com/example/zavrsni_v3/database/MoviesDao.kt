package com.example.zavrsni_v3.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import com.example.zavrsni_v3.model.Movie
import com.example.zavrsni_v3.model.MovieDetails

@Dao
interface MoviesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addMovie(movie: MovieDetails)

    @Query("SELECT * FROM movies_table ORDER BY Title DESC")
    fun getAllMovies(): LiveData<List<MovieDetails>>

    @Query("SELECT * FROM movies_table WHERE imdbID=:imdbID")
    suspend fun getMovie(imdbID: String): MovieDetails

    @Delete
    suspend fun deleteMovie(movie: MovieDetails)

    @Query("SELECT * FROM movies_table WHERE Genre LIKE '%' || :genre || '%' AND Year LIKE '%' || :year || '%' ORDER BY :sortBy")
    fun getMoviesFiltered(genre: String, year: String, sortBy: String): LiveData<List<MovieDetails>>

    @Query("SELECT * FROM movies_cache ORDER BY Title DESC")
    fun getAllMoviesCache(): LiveData<List<Movie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMoviesToCache(movie: Movie)

    @Query("DELETE FROM movies_cache")
    suspend fun deleteCache()

}