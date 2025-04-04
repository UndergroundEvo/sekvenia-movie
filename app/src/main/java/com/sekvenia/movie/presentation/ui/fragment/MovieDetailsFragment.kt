package com.sekvenia.movie.presentation.ui.fragment


import android.os.Bundle
import android.view.View
import androidx.core.bundle.bundleOf
import androidx.fragment.app.Fragment
import coil.load
import com.github.terrakok.cicerone.Router
import com.sekvenia.movie.R
import com.sekvenia.movie.databinding.MovieScreenLayoutBinding
import com.sekvenia.movie.domain.entity.Film
import com.sekvenia.movie.domain.entity.toFilm
import org.koin.android.ext.android.inject
import java.math.RoundingMode


class MovieDetailsFragment : Fragment(R.layout.movie_screen_layout) {
    private val router: Router by inject()

    companion object {
        private const val ARG_FILM = "film_json"
        fun newInstance(filmJson: String): MovieDetailsFragment {
            return MovieDetailsFragment().apply {
                arguments = bundleOf(ARG_FILM to filmJson)
            }
        }
    }

    private var _binding: MovieScreenLayoutBinding? = null
    private val binding get() = _binding!!
    private lateinit var film: Film

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        film = arguments?.getString(ARG_FILM)?.toFilm()!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = MovieScreenLayoutBinding.bind(view)

        film.let { movie ->
            with(binding) {
                topAppBar.title = movie.name
                movieImage.load(movie.imageUrl) { error(R.drawable.placeholder) }
                movieImage.contentDescription = movie.localizedName
                movieTitle.text = movie.localizedName
                genreYear.text = buildString {
                    append(movie.genres.joinToString(", "))
                    if (movie.genres.isNotEmpty()) append(", ")
                    append(movie.year)
                    append(" ")
                    append(getString(R.string.year_word))
                }
                rating.text = movie.rating?.let {
                    it.toBigDecimal().setScale(1, RoundingMode.UP).toDouble().toString()
                } ?: getString(R.string.no_rating)
                kinopoisk.text = getString(R.string.kinopoisk)
                description.text = movie.description
                topAppBar.setNavigationOnClickListener { onBackPressed() }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onBackPressed() = router.exit()

}