package com.sekvenia.movie.presentation.ui.compose.movie_list.movie_card

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sekvenia.movie.domain.entity.Film

@Composable
fun MovieCardTitle(movie: Film) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        color = Color.Black,
        textAlign = TextAlign.Start,
        fontWeight = FontWeight.Bold,
        text = movie.localizedName,
        fontSize = 16.sp,
        lineHeight = 20.sp,
        minLines = 2,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis,
    )
}