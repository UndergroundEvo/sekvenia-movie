package com.sekvenia.movie.presentation.ui.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.sekvenia.movie.R
import com.sekvenia.movie.domain.entity.Film
import com.sekvenia.movie.presentation.ui.compose.elements.LoadingScreen
import com.sekvenia.movie.presentation.ui.compose.movie_list.MovieList
import com.sekvenia.movie.presentation.ui.compose.movie_list.MovieTopAppBar
import com.sekvenia.movie.presentation.viewmodel.MovieListState
import com.sekvenia.movie.presentation.viewmodel.MovieListViewModel

@Composable
fun MoviesListScreen(
    onFilmClick: (Film) -> Unit,
    viewModel: MovieListViewModel
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val filter by viewModel.filters.collectAsState()

    LaunchedEffect(state is MovieListState.Error) {
        if (state is MovieListState.Error) {
            val result = snackbarHostState.showSnackbar(
                message = context.getString(R.string.screen_network_error),
                actionLabel = context.getString(R.string.retry_word),
                duration = SnackbarDuration.Indefinite
            )
            when (result) {
                SnackbarResult.Dismissed -> { }
                SnackbarResult.ActionPerformed -> {
                    viewModel.getFilmsSorted()
                }
            }
        }
    }

    Scaffold(
        topBar = { MovieTopAppBar() },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { contentPadding ->
        Box(
            modifier = Modifier
                .padding(contentPadding)
                .fillMaxSize()
        ) {
            when (state) {
                is MovieListState.Content -> {
                    val movies = (state as MovieListState.Content).movies
                    MovieList(movies, filter, onFilmClick, viewModel)
                }
                is MovieListState.Error -> Unit
                MovieListState.Initial -> {}
                MovieListState.Loading -> LoadingScreen(modifier = Modifier.padding(contentPadding))
            }
        }
    }
}