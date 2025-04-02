package com.sekvenia.movie.presentation.ui.compose.movie_list.movie_card

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.sekvenia.movie.domain.entity.Film

@Composable
fun MovieCard(
    movie: Film,
    onFilmClick: (Film) -> Unit
) {
    val imageRound = 4.dp

    Box(
        modifier = Modifier
            .height(270.dp)
            .fillMaxWidth()
            .clickable { onFilmClick(movie) },
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.8f)
            ) {
                SubcomposeAsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(imageRound)),
                    contentScale = ContentScale.Crop,
                    model = movie.imageUrl,
                    alignment = Alignment.Center,
                    contentDescription = movie.name,
                ) {
                    val state = painter.state
                    if (state is AsyncImagePainter.State.Loading || state is AsyncImagePainter.State.Error) {
                        ErrorImage(imageRound)
                    } else {
                        SubcomposeAsyncImageContent()
                    }
                }
            }
            MovieCardTitle(movie)
        }
    }
}