package com.sekvenia.movie

import android.app.Application
import com.sekvenia.movie.di.networkModule
import com.sekvenia.movie.di.repositoryModule
import com.sekvenia.movie.di.useCaseModule
import com.sekvenia.movie.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class MoviesApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MoviesApp)
            androidLogger() //todo remove
            modules(
                networkModule,
                repositoryModule,
                useCaseModule,
                viewModelModule
            )
        }
    }
}
