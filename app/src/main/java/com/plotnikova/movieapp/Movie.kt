package com.plotnikova.movieapp

data class Movie(
    val id: Int,
    val title: String,
    val year: Int,
    val genre: String,
    val rating: Double,
    val director: String,
    val description: String
)