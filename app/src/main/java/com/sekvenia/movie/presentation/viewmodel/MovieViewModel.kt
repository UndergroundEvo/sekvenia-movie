package com.sekvenia.movie.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sekvenia.movie.common.Resource
import com.sekvenia.movie.data.models.FilmDto
import com.sekvenia.movie.domain.usecase.GetFilmsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MovieViewModel(
    private val getFilmsUseCase: GetFilmsUseCase,
) : ViewModel() {
        private val _state = MutableStateFlow<MovieStateSealed>(MovieStateSealed.Initial)
    val state: StateFlow<MovieStateSealed> = _state.asStateFlow()

    init {
        getFilms()
    }

    fun getFilms() {
        _state.value = MovieStateSealed.Loading
        try {
            //val response = repository.getMovies()
            val response = getFilmsUseCase.getFilms()
            response.onEach { item ->
                when (item) {
                    is Resource.Error -> _state.value = MovieStateSealed.Error("")
                    is Resource.Loading -> _state.value = MovieStateSealed.Loading
                    is Resource.Success -> _state.value = MovieStateSealed.Content(
                        movies = item.data!!
                    )
                }
            }.launchIn(viewModelScope)
        } catch (e: Exception) {
            _state.value = MovieStateSealed.Error(e.localizedMessage ?: "Unexpected error")
        }
    }

    fun collectUniqueGenres(films: List<FilmDto>): List<String> {
        return films
            .flatMap { it.genres ?: emptyList() }
            .distinct()
    }
}