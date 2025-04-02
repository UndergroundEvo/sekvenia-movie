package com.sekvenia.movie.data.models

import com.google.gson.annotations.SerializedName
import com.sekvenia.movie.domain.entity.Film

data class FilmDto(
    val id: Int,
    @SerializedName("localized_name")
    val localizedName: String,
    val name: String,
    val year: Int,
    val rating: Double? = null, //отсуствует у id 841340
    @SerializedName("image_url")
    val imageUrl: String? = null, //отсуствует у id 841340
    val description: String? = null, //отсуствует у id 512280
    val genres: List<String> = emptyList()
)

fun FilmDto.toFilm() = Film(
    id = id,
    localizedName = localizedName,
    name = name,
    year = year,
    rating = rating,
    imageUrl = imageUrl,
    description = description,
    genres = genres
)