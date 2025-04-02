package com.sekvenia.movie.presentation.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.github.terrakok.cicerone.Router
import com.sekvenia.movie.R
import com.sekvenia.movie.domain.entity.Film
import com.sekvenia.movie.navigation.Screens
import com.sekvenia.movie.presentation.theme.MoviesTheme
import com.sekvenia.movie.presentation.ui.compose.MoviesListScreen
import com.sekvenia.movie.presentation.viewmodel.MovieListViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieListFragment : Fragment(R.layout.movie_list_layout) {
    private val router: Router by inject()
    private val viewModel by viewModel<MovieListViewModel>()

    private fun openMovieDetails(film: Film) {
        router.navigateTo(Screens.movieDetail(film))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                MoviesTheme {
                    MoviesListScreen(
                        { film -> openMovieDetails(film) }, viewModel
                    )
                }
            }
        }
    }
}