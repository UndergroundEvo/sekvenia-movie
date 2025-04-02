package com.sekvenia.movie.presentation.ui.compose.movie_list

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import java.util.Locale


@Composable
fun HeaderGenre(item: String) {
    Text(
        text = item.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() },
        color = Color.Black,
        fontSize = 16.sp,
    )
}