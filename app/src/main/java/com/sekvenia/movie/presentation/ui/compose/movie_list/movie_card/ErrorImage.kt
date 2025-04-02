package com.sekvenia.movie.presentation.ui.compose.movie_list.movie_card

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import com.sekvenia.movie.R

@Composable
fun ErrorImage(imageRound: Dp) {
    Image(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .clip(RoundedCornerShape(imageRound)),
        alignment = Alignment.Center,
        painter = painterResource(R.drawable.placeholder),
        contentScale = ContentScale.Crop,
        contentDescription = stringResource(R.string.error_image)
    )
}