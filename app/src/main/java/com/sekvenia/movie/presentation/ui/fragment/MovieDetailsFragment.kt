package com.sekvenia.movie.presentation.ui.fragment

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.RelativeSizeSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import coil.load
import com.google.android.material.appbar.MaterialToolbar
import com.sekvenia.movie.R
import com.sekvenia.movie.data.models.FilmDto

class MovieDetailsFragment : Fragment(R.layout.movie_screen_layout) {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.movie_screen_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val testMovie = arguments?.getParcelable<FilmDto>("film")
        val toolbar : MaterialToolbar = view.findViewById(R.id.topAppBar)
        val movieImage: ImageView = view.findViewById(R.id.movieImage)
        val movieTitle: TextView = view.findViewById(R.id.movieTitle)
        val genreYear: TextView = view.findViewById(R.id.genreYear)
        val rating: TextView = view.findViewById(R.id.rating)

        testMovie?.let {
            toolbar.setTitle(testMovie.name)
            movieImage.load(testMovie.imageUrl)
            movieTitle.text = testMovie.localizedName

            genreYear.text = buildString {
                append(testMovie.genres.joinToString(", "))
                append(" ")
                append(testMovie.year)
                append(" ")
                append(getString(R.string.year_word))
            }
            if (testMovie.rating == null) {
                rating.text = ""
            } else {
                val fullText = testMovie.rating.toString() + " " + getString(R.string.kinopoisk)
                val spannable = SpannableString(fullText)
                spannable.setSpan(RelativeSizeSpan(1.8f),0,
                    5,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                spannable.setSpan(
                    RelativeSizeSpan(1.0f),5,
                    fullText.length,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                rating.text = spannable
            }
            val description : TextView = view.findViewById(R.id.description)
            description.text = testMovie.description
        }
        toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressed() //не deprecated требует api33
        }
        toolbar.setBackgroundColor(resources.getColor(R.color.main_color))
        toolbar.setTitleTextColor(resources.getColor(R.color.white))
        toolbar.setNavigationIconTint(resources.getColor(R.color.white))
        rating.setTextColor(resources.getColor(R.color.main_color))
    }
}