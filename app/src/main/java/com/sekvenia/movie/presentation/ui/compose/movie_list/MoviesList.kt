package com.sekvenia.movie.presentation.ui.compose.movie_list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.sekvenia.movie.R
import com.sekvenia.movie.domain.entity.Film
import com.sekvenia.movie.presentation.ui.compose.movie_list.movie_card.MovieCard
import com.sekvenia.movie.presentation.viewmodel.MovieListViewModel

@Composable
fun MovieList(
    movies: List<Film>,
    filter: String = "",
    onFilmClick: (Film) -> Unit,
    viewModel: MovieListViewModel
) {
    var selectedGenre by remember { mutableStateOf(filter) }
    val filteredMovies = if (selectedGenre == "") {
        movies
    } else {
        movies.filter { it.genres.contains(selectedGenre) }
    }
    val collectUniqueGenres = { films: List<Film> ->
        films.flatMap { it.genres }.distinct()
    }

    LaunchedEffect(selectedGenre) { viewModel.setFilters(selectedGenre) }

    LazyColumn(Modifier.fillMaxSize().padding(bottom = 8.dp)) {
        item {
            HeaderText(
                stringResource(R.string.genres_word),
                Modifier.padding(top = 8.dp)
            )
        }
        collectUniqueGenres(movies).onEach { item ->
            item {
                Box(
                    contentAlignment = Alignment.CenterStart,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { selectedGenre = if (selectedGenre == item) "" else item }
                        .background(
                            if (selectedGenre == item) colorResource(R.color.accent_color)
                            else Color.White
                        )
                        .padding(16.dp, 10.dp),
                ) {
                    HeaderGenre(item)
                }
            }
        }
        item {
            HeaderText(
                stringResource(R.string.movies_word),
                Modifier.padding(top = 16.dp)
            )
        }
        items(filteredMovies.chunked(2)) { items ->
            Row(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                for ((index, item) in items.withIndex()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(1f / (2 - index))
                    ) {
                        MovieCard(movie = item, onFilmClick)
                    }
                }
            }
        }
    }
}
