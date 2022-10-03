package com.example.zavrsni_v3.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "movies_table")
data class MovieDetails(
    @PrimaryKey()
    val imdbID: String,
    val Title: String,
    val Year: String,
    val imdbRating: String,
    val Released: String,
    val Genre: String,
    val Writer: String,
    val Actors: String,
    val Language: String,
    val Plot: String,
    val Awards: String,
    val Runtime: String,
    val Poster: String,
    val Type: String
)