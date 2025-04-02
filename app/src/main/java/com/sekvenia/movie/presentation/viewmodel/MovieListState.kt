package com.sekvenia.movie.presentation.viewmodel

import com.sekvenia.movie.domain.entity.Film

sealed interface MovieListState {
    data object Initial : MovieListState

    data object Loading : MovieListState

    data class Content(
        var movies: List<Film>
    ) : MovieListState

    data class Error(val message: String) : MovieListState
}