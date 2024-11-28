package com.sekvenia.movie.presentation.viewmodel

import com.sekvenia.movie.data.models.CinemaDto

sealed interface MovieStateSealed {
    data object Initial : MovieStateSealed

    data object Loading : MovieStateSealed

    data class Content(
        var movies: CinemaDto
    ) : MovieStateSealed

    data class Error(val message: String) : MovieStateSealed
}