package com.sekvenia.movie.presentation.ui.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.sekvenia.movie.R
import com.sekvenia.movie.common.Constans
import com.sekvenia.movie.data.models.FilmDto
import com.sekvenia.movie.ui.theme.getBackgroundColor


@Composable
fun MovieCard(
    movie: FilmDto,
    onFilmClick: (FilmDto) -> Unit
) {

    Card(
        modifier = Modifier
            .defaultMinSize(160.dp, 340.dp)
            .width(160.dp)
            .height(340.dp)
            .padding(4.dp, 8.dp),
        shape = RoundedCornerShape(0.dp),
        colors = getBackgroundColor(),
        onClick = {onFilmClick(movie)}
    ) {
        Column {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                SubcomposeAsyncImage(
                    modifier = Modifier.fillMaxWidth(),
                    contentScale = ContentScale.FillWidth,
                    model = movie.imageUrl,
                    alignment = Alignment.Center,
                    contentDescription = movie.name,
                ) {
                    val state = painter.state
                    if (state is AsyncImagePainter.State.Loading || state is AsyncImagePainter.State.Error) {
                        Box(
                            modifier = Modifier
                            .size(160.dp,265.dp),
                            contentAlignment = Alignment.Center
                        ){
                            AsyncImage(
                                modifier = Modifier.fillMaxWidth(),
                                model = Constans.ERROR_IMAGE,
                                contentScale = ContentScale.FillWidth,
                                contentDescription = stringResource(R.string.error_image),
                            )
                        }
                    } else {
                        SubcomposeAsyncImageContent()
                    }
                }
            }
            Box(
                modifier = Modifier.fillMaxHeight()
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 2.dp)
                        .align(Alignment.Center),
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.Bold,
                    text = movie.localizedName,
                    fontSize = 13.sp,
                    lineHeight = 20.sp,
                    minLines = 1,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,

                )
            }
        }
    }
}