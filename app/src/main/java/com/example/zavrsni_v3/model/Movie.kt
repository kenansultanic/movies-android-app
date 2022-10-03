package com.example.zavrsni_v3.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies_cache")
data class Movie(
    @PrimaryKey
    val imdbID: String,
    val Poster: String,
    val Title: String,
    val Type: String,
    val Year: String
)
