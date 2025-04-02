package com.sekvenia.movie.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.sekvenia.movie.common.Resource
import com.sekvenia.movie.data.models.CinemaDto
import com.sekvenia.movie.data.models.FilmDto
import com.sekvenia.movie.data.models.toFilm
import com.sekvenia.movie.domain.usecase.GetFilmsUseCase
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import java.io.IOException

@ExperimentalCoroutinesApi
class MovieListViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: MovieListViewModel
    private val getFilmsUseCase: GetFilmsUseCase = mockk()
    private val savedStateHandle: SavedStateHandle = mockk(relaxed = true)

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        every { savedStateHandle.get<String>(FILTER_KEY) } returns null
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `init should load films and restore filters`() = runTest {
        // Arrange
        val filter = "test_filter"
        every { savedStateHandle.get<String>(FILTER_KEY) } returns filter

        // Act
        viewModel = MovieListViewModel(getFilmsUseCase, savedStateHandle)
        advanceUntilIdle()

        // Assert
        assertEquals(filter, viewModel.filters.value)
        verify { savedStateHandle.get<String>(FILTER_KEY) }
    }

    @Test
    fun `getFilmsSorted should emit Content state with sorted films on success`() = runTest {
        // Arrange
        val testFilms = listOf(
            FilmDto(
                id = 326,
                localizedName = "Побег из Шоушенка",
                name = "The Shawshank Redemption",
                year = 19984,
                rating = null,
                imageUrl = null,
                description = null,
                genres = emptyList()
            ),
            FilmDto(
                id = 326,
                localizedName = "Побег из Шоушенка",
                name = "The Shawshank Redemption",
                year = 19984,
                rating = null,
                imageUrl = null,
                description = null,
                genres = emptyList()
            )
        )
        val expectedFilms = testFilms.map { it.toFilm() }.sortedBy { it.localizedName.lowercase() }
        coEvery { getFilmsUseCase.getFilms() } returns flowOf(Resource.Success(CinemaDto(testFilms)))

        // Act
        viewModel = MovieListViewModel(getFilmsUseCase, savedStateHandle)
        advanceUntilIdle()

        // Assert
        val state = viewModel.state.value as MovieListState.Content
        assertEquals(expectedFilms, state.movies)
    }

    @Test
    fun `getFilmsSorted should emit Error state on exception`() = runTest {
        // Arrange
        coEvery { getFilmsUseCase.getFilms() } throws IOException("Network error")

        // Act
        viewModel = MovieListViewModel(getFilmsUseCase, savedStateHandle)
        advanceUntilIdle()

        // Assert
        assertTrue(viewModel.state.value is MovieListState.Error)
    }

    @Test
    fun `setFilters should update filters and save to SavedStateHandle`() {
        // Arrange
        viewModel = MovieListViewModel(getFilmsUseCase, savedStateHandle)
        val newFilter = "new_filter"

        // Act
        viewModel.setFilters(newFilter)

        // Assert
        assertEquals(newFilter, viewModel.filters.value)
        verify { savedStateHandle[FILTER_KEY] = newFilter }
    }
}