package com.sekvenia.movie.navigation

import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.sekvenia.movie.domain.entity.Film
import com.sekvenia.movie.domain.entity.toJson
import com.sekvenia.movie.presentation.ui.fragment.MovieDetailsFragment
import com.sekvenia.movie.presentation.ui.fragment.MovieListFragment

object Screens {
    fun movieList() = FragmentScreen { MovieListFragment() }
    fun movieDetail(film: Film) = FragmentScreen {
        MovieDetailsFragment.newInstance(film.toJson())
    }
}
