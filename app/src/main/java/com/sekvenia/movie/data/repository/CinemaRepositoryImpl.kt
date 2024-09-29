package com.sekvenia.movie.data.repository

import com.sekvenia.movie.data.models.CinemaDto
import com.sekvenia.movie.data.network.CinemaApi
import com.sekvenia.movie.domain.repository.CinemaRepository

class CinemaRepositoryImpl(
    private val api: CinemaApi
): CinemaRepository {

    override suspend fun getMovies() : CinemaDto {
        return api.getMovies()
    }
}