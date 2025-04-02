package com.sekvenia.movie.domain.entity

import com.google.gson.Gson
import com.sekvenia.movie.data.models.FilmDto

data class Film(
    val id: Int,
    val localizedName: String,
    val name: String,
    val year: Int,
    val rating: Double? = null,
    val imageUrl: String? = null,
    val description: String? = null,
    val genres: List<String> = emptyList()
)
fun Film.toFilmDto() = FilmDto(
    id = id,
    localizedName = localizedName,
    name = name,
    year = year,
    rating = rating,
    imageUrl = imageUrl,
    description = description,
    genres = genres
)

fun Film.toJson() = Gson().toJson(this)

fun String.toFilm() = Gson().fromJson(this, Film::class.java)

fun Film.mock() = Film(
    id = 326,
    localizedName = "Побег из Шоушенка",
    name = "The Shawshank Redemption",
    year = 19984,
    rating = null,
    imageUrl = null,
    description = null,
    genres = emptyList()
)