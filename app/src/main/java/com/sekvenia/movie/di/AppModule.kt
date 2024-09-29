package com.sekvenia.movie.di

import com.sekvenia.movie.common.Constans
import com.sekvenia.movie.data.models.FilmDto
import com.sekvenia.movie.data.network.CinemaApi
import com.sekvenia.movie.data.repository.CinemaRepositoryImpl
import com.sekvenia.movie.domain.repository.CinemaRepository
import com.sekvenia.movie.domain.usecase.GetFilmsUseCase
import com.sekvenia.movie.presentation.viewmodel.DetailViewModel
import com.sekvenia.movie.presentation.viewmodel.MovieViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single {
        Retrofit.Builder()
            .baseUrl(Constans.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CinemaApi::class.java)
    }
}
val repositoryModule = module {
    single<CinemaRepository> { CinemaRepositoryImpl(get()) }
}
val useCaseModule = module {
    single { GetFilmsUseCase(get()) }
}
val viewModelModule = module {
    viewModel { MovieViewModel(get()) }
    viewModel { (filmDto: FilmDto) -> DetailViewModel(filmDto) }
}