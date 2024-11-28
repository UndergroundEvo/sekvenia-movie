package com.sekvenia.movie.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.CardColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext


@Composable
fun getHeadlineColor() = if (isSystemInDarkTheme()) Color.White else Color.Black

@Composable
fun getContainerColor(
    selectedGenre: String,
    item: String
) = if (selectedGenre == item) Color(0xFFFFC967)
else if (isSystemInDarkTheme()) Color.Black else Color.White

@Composable
fun getBackgroundColor(): CardColors {
    return if (isSystemInDarkTheme()) CardColors(
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = Color.White,
        disabledContainerColor = Color.Gray,
        disabledContentColor = Color.DarkGray
    )
    else CardColors(
        containerColor = Color.White,
        contentColor = Color.Black,
        disabledContainerColor = Color.Gray,
        disabledContentColor = Color.DarkGray
    )
}

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    inversePrimary = Color(0xFFFFC967),
    tertiary = Pink80
)

/*
    основной цвет - Color(0xFF0E3165)
    акцентный цвет - Color(0xFFFFC967)
#*/

private val LightColorScheme = lightColorScheme(
    /*    primary = Purple40,
        secondary = PurpleGrey40,
        tertiary = Pink40*/
    primary = Color.White,
    secondary = Color.White,
    tertiary = Color.White,
    inversePrimary = Color(0xFFFFC967),

    background = Color(0xFFFFFBFE),
    surface = Color(0xFF0E3165),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color.Black,
    onSurface = Color(0xFFFFFBFE),
    )

@Composable
fun MoviesTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false, // M3 OFFLINE
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}