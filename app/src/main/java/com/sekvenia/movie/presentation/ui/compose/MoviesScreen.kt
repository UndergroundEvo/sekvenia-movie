package com.sekvenia.movie.presentation.ui.compose

import MovieListL
import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.sekvenia.movie.R
import com.sekvenia.movie.data.models.FilmDto
import com.sekvenia.movie.presentation.ui.compose.portrait.MovieListP
import com.sekvenia.movie.presentation.viewmodel.MovieStateSealed
import com.sekvenia.movie.presentation.viewmodel.MovieViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoviesScreen(
    onFilmClick: (FilmDto) -> Unit
) {
    val context = LocalContext.current
    val viewModel = koinViewModel<MovieViewModel>()
    val state by viewModel.state.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    var orientation by remember { mutableStateOf(Configuration.ORIENTATION_PORTRAIT) }
    val configuration = LocalConfiguration.current

    LaunchedEffect(configuration) {
        snapshotFlow { configuration.orientation }
            .collect { orientation = it }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(
                    text = stringResource(R.string.movies_word),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(align= Alignment.CenterHorizontally)
                )
            })
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { contentPadding  ->
        Box(
            modifier = Modifier.padding(contentPadding)
                .fillMaxSize()
        ){
            when (state) {
                is MovieStateSealed.Content -> {
                    val movies = (state as MovieStateSealed.Content).movies.film
                    when (orientation) {
                        Configuration.ORIENTATION_LANDSCAPE -> {
                            MovieListL(movies, onFilmClick)
                        }

                        else -> {
                            MovieListP(movies, onFilmClick)
                        }
                    }
                }
                is MovieStateSealed.Error -> scope.launch {
                    val result = snackbarHostState.showSnackbar(
                        message = context.getString(R.string.screen_network_error),
                        actionLabel = context.getString(R.string.retry_word),
                        duration = SnackbarDuration.Indefinite
                    )
                    when (result){
                        SnackbarResult.Dismissed -> {} //todo
                        SnackbarResult.ActionPerformed -> {viewModel.getFilms()}
                    }
                }
                MovieStateSealed.Initial -> {}
                MovieStateSealed.Loading -> LoadingScreen(modifier = Modifier.padding(contentPadding))
            }
        }
    }
}