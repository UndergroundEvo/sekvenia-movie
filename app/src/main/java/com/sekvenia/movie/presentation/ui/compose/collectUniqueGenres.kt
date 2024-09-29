package com.sekvenia.movie.presentation.ui.compose

import com.sekvenia.movie.data.models.FilmDto

//перекинуть в viewmodel
fun collectUniqueGenres(films: List<FilmDto>): List<String> {
    return films
        .flatMap { it.genres ?: emptyList() }
        .distinct()
}
