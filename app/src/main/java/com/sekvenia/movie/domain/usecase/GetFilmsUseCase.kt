package com.sekvenia.movie.domain.usecase

import com.sekvenia.movie.R
import com.sekvenia.movie.common.Resource
import com.sekvenia.movie.data.models.CinemaDto
import com.sekvenia.movie.domain.repository.CinemaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class GetFilmsUseCase(
    private val repository: CinemaRepository
) {
    fun getFilms(): Flow<Resource<CinemaDto>> = flow {
        try {
            emit(Resource.Loading())
            val movies = repository.getMovies()
            emit(Resource.Success<CinemaDto>(movies))
        } catch (e: HttpException){
            emit(Resource.Error<CinemaDto>(e.localizedMessage ?: R.string.http_error.toString()))
        } catch (e: IOException){
            emit(Resource.Error<CinemaDto>(e.localizedMessage ?: R.string.io_error.toString()))
        }
    }
}
