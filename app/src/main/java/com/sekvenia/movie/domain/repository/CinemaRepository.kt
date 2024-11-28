package com.sekvenia.movie.domain.repository

import com.sekvenia.movie.data.models.CinemaDto

interface CinemaRepository {
    suspend fun getMovies() : CinemaDto
}