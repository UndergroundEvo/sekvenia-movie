package com.sekvenia.movie.data.network

import com.sekvenia.movie.data.models.CinemaDto
import retrofit2.http.GET

interface CinemaApi {
    @GET("sequeniatesttask/films.json")
    suspend fun getMovies() : CinemaDto
}