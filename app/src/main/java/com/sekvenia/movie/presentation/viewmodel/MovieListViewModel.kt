package com.sekvenia.movie.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sekvenia.movie.common.Resource
import com.sekvenia.movie.data.models.toFilm
import com.sekvenia.movie.domain.usecase.GetFilmsUseCase
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

const val FILTER_KEY = "filter_key"

class MovieListViewModel(
    private val getFilmsUseCase: GetFilmsUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow<MovieListState>(MovieListState.Initial)
    val state: StateFlow<MovieListState> = _state.asStateFlow()

    private val _filters = MutableStateFlow(savedStateHandle.get<String>(FILTER_KEY) ?: "")
    val filters: StateFlow<String> get() = _filters

    init {
        getFilmsSorted()
    }

    fun getFilmsSorted() {
        _state.value = MovieListState.Loading
        try {
            val response = getFilmsUseCase.getFilms()
            response.onEach { item ->
                when (item) {
                    is Resource.Error -> _state.value = MovieListState.Error("Loading error")
                    is Resource.Loading -> _state.value = MovieListState.Loading
                    is Resource.Success -> _state.value = MovieListState.Content(
                        movies = item.data?.film
                            ?.map { it.toFilm() }
                            ?.sortedBy { it.localizedName.lowercase() }
                            ?: emptyList()
                    )
                }
            }.launchIn(viewModelScope)
        } catch (e: Exception) {
            _state.value = MovieListState.Error(e.localizedMessage ?: "Unexpected error")
        } catch (_: CancellationException){}
    }

    fun setFilters(filter: String) {
        _filters.value = filter
        savedStateHandle[FILTER_KEY] = filter
    }
}