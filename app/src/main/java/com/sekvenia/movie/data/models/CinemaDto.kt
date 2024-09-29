package com.sekvenia.movie.data.models


import com.google.gson.annotations.SerializedName

data class CinemaDto(
    @SerializedName("films")
    val film: List<FilmDto>
)