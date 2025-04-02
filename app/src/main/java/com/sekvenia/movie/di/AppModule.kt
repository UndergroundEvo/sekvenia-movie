package com.sekvenia.movie.di

import androidx.lifecycle.SavedStateHandle
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router
import com.sekvenia.movie.common.Constants
import com.sekvenia.movie.data.network.CinemaApi
import com.sekvenia.movie.data.repository.CinemaRepositoryImpl
import com.sekvenia.movie.domain.repository.CinemaRepository
import com.sekvenia.movie.domain.usecase.GetFilmsUseCase
import com.sekvenia.movie.presentation.viewmodel.MovieListViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single {
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
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
    viewModel { (handle: SavedStateHandle) ->
        MovieListViewModel(getFilmsUseCase = get(), savedStateHandle = handle)
    }
}

val navigationModule = module {
    single { Router() }
    single { Cicerone.create(get<Router>()) }
    single { get<Cicerone<Router>>().getNavigatorHolder() }
}
