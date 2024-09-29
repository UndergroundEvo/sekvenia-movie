package com.sekvenia.movie.presentation.ui.compose.portrait

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.sekvenia.movie.R
import com.sekvenia.movie.data.models.FilmDto
import com.sekvenia.movie.presentation.ui.compose.MovieCard
import com.sekvenia.movie.presentation.ui.compose.collectUniqueGenres
import com.sekvenia.movie.ui.theme.getContainerColor
import com.sekvenia.movie.ui.theme.getHeadlineColor


@Composable
fun MovieListP(
    movies: List<FilmDto>,
    onFilmClick: (FilmDto) -> Unit
) {
    var selectedGenre by remember { mutableStateOf("") }
    val filteredMovies = if (selectedGenre == "") {
        movies
    } else {
        movies.filter { it.genres.contains(selectedGenre) }
    }

    Column(
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        Text(
            modifier = Modifier
                .padding(8.dp, 16.dp)
                .clickable { selectedGenre = "" },
            text = stringResource(R.string.genres_word),
            fontWeight = FontWeight.Bold
        )
        collectUniqueGenres(movies).onEach { item ->
            ListItem(
                modifier = Modifier.clickable {
                    selectedGenre = item
                },
                headlineContent = {
                    Text(
                        text = item,
                        textAlign = TextAlign.Start,
                        modifier = Modifier.padding(6.dp, 12.dp)
                    )
                },
                colors = ListItemDefaults.colors(
                    containerColor =
                    getContainerColor(selectedGenre, item),
                    headlineColor = getHeadlineColor()
                )
            )
        }
        LazyVerticalGrid(
            modifier = Modifier.height(190.dp * filteredMovies.size.inc()), // на самом деле нормальная тема (подсказали в ЦФТ)
            // в идеале считается от dp устройства
            // для обхода проблемы grid in scrollable Column
            userScrollEnabled = false,
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(8.dp),

            ) {
            items(filteredMovies) { movies ->
                MovieCard(movie = movies, onFilmClick)
            }
        }
    }
}