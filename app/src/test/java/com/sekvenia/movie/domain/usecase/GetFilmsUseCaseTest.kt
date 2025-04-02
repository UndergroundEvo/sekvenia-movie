package com.sekvenia.movie.domain.usecase

import com.sekvenia.movie.common.Resource
import com.sekvenia.movie.data.models.CinemaDto
import com.sekvenia.movie.data.models.FilmDto
import com.sekvenia.movie.domain.repository.CinemaRepository
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import retrofit2.Response
import java.io.IOException
import kotlin.test.Test

@ExperimentalCoroutinesApi
class GetFilmsUseCaseTest {

    private lateinit var useCase: GetFilmsUseCase
    private val repository: CinemaRepository = mockk()

    @Before
    fun setup() {
        useCase = GetFilmsUseCase(repository)
    }

    @Test
    fun `getFilms should emit Loading and Success when repository returns movies`() = runTest {
        // Arrange
        val testMovies = CinemaDto(
            listOf(
                FilmDto(
                    id = 1,
                    localizedName = "Фильм 1",
                    name = "Movie 1",
                    year = 2020,
                    rating = 8.5,
                    imageUrl = "url",
                    description = "Description",
                    genres = listOf("Action")
                )
            )
        )
        coEvery { repository.getMovies() } returns testMovies

        // Act
        val result = useCase.getFilms().toList()

        // Assert
        assertTrue(result[0] is Resource.Loading)
        assertTrue(result[1] is Resource.Success)
        assertEquals(testMovies, (result[1] as Resource.Success).data)
    }

    @Test
    fun `getFilms should emit Loading and Error when repository throws HttpException`() = runTest {
        // Arrange
        coEvery { repository.getMovies() } throws retrofit2.HttpException(
            Response.error<Any>(
                500,
                "".toResponseBody(null)
            )
        )

        // Act
        val result = useCase.getFilms().toList()

        // Assert
        assertTrue(result[0] is Resource.Loading)
        assertTrue(result[1] is Resource.Error)
    }

    @Test
    fun `getFilms should emit Loading and Error when repository throws IOException`() = runTest {
        // Arrange
        coEvery { repository.getMovies() } throws IOException("Network error")

        // Act
        val result = useCase.getFilms().toList()

        // Assert
        assertTrue(result[0] is Resource.Loading)
        assertTrue(result[1] is Resource.Error)
        assertEquals("Network error", (result[1] as Resource.Error).message)
    }
}
