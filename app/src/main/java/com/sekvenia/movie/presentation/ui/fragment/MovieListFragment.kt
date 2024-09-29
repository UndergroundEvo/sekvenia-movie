package com.sekvenia.movie.presentation.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.sekvenia.movie.R
import com.sekvenia.movie.data.models.FilmDto
import com.sekvenia.movie.presentation.ui.compose.MoviesScreen
import com.sekvenia.movie.ui.theme.MoviesTheme

class MovieListFragment : Fragment(R.layout.movie_list_layout) {

    private fun openMovieDetails(filmDto: FilmDto) {
        val bundle = Bundle().apply {
            putParcelable("film", filmDto)
        }
        findNavController().navigate(R.id.action_movieListFragment_to_movieDetailsFragment, bundle)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                MoviesTheme {
                    MoviesScreen(onFilmClick = { filmDto ->
                        openMovieDetails(filmDto)
                    })
                }
            }
        }
    }
}