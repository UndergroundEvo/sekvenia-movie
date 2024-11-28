import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sekvenia.movie.data.models.FilmDto
import com.sekvenia.movie.presentation.ui.compose.MovieCard
import com.sekvenia.movie.presentation.ui.compose.collectUniqueGenres
import com.sekvenia.movie.ui.theme.getContainerColor

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MovieListL(
    movies: List<FilmDto>,
    onFilmClick: (FilmDto) -> Unit
) {
    var selectedGenre by remember { mutableStateOf("") }
    val genres = collectUniqueGenres(movies)
    val filteredMovies = if (selectedGenre == "") {
        movies
    } else {
        movies.filter { it.genres.contains(selectedGenre) }
    }

    Column {
        FlowRow {
            repeat(collectUniqueGenres(movies).size) { index ->
                FilterChip(
                    modifier = Modifier.padding(vertical = 2.dp, horizontal = 6.dp),
                    selected = genres[index] == selectedGenre,
                    onClick = {
                        if (genres[index] == selectedGenre) selectedGenre = ""
                        else selectedGenre = genres[index]
                    },
                    label = { Text(genres[index]) },
                    colors = FilterChipDefaults.filterChipColors().copy(
                        selectedContainerColor =
                        getContainerColor(selectedGenre, genres[index]),

                    )
                )

            }
        }
        LazyVerticalGrid(
            columns = GridCells.Fixed(4),
            contentPadding = PaddingValues(8.dp),

            ) {
            items(filteredMovies) { movies ->
                MovieCard(movie = movies, onFilmClick)
            }
        }
    }
}