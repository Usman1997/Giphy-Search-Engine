/*
 Created by Usman Siddiqui
 */
package com.example.giphysearchengine.ui.search

import app.cash.turbine.test
import com.example.giphysearchengine.repository.FakeSearchRepository
import com.example.giphysearchengine.utils.Constants
import org.junit.Before
import org.junit.Test
import com.example.giphysearchengine.utils.Rating
import com.example.giphysearchengine.utils.State
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import kotlin.time.ExperimentalTime


class SearchViewModelTest {


    private lateinit var fakeSearchRepository: FakeSearchRepository

    private lateinit var searchViewModel: SearchViewModel

    @Before
    fun setup() {
        fakeSearchRepository = FakeSearchRepository()
        searchViewModel = SearchViewModel(fakeSearchRepository)
    }

    @Test
    fun `is last page not divisible by QUERY PER PAGE is not equals to current page, returns false`() {
        searchViewModel.setCurrentPage(0)
        Assert.assertFalse(searchViewModel.isLastPage(64))
    }

    @Test
    fun `is last page not divisible by QUERY PER PAGE is equal to current page, returns true`() {
        searchViewModel.setCurrentPage(4)
        Assert.assertTrue(searchViewModel.isLastPage(64))
    }

    @Test
    fun `is last page divisible by QUERY PER PAGE is equal to current page, returns false`() {
        searchViewModel.setCurrentPage(0)
        Assert.assertFalse(searchViewModel.isLastPage(60))
    }

    @Test
    fun `is last page divisible by QUERY PER PAGE is equal to current page, returns true`() {
        searchViewModel.setCurrentPage(3)
        Assert.assertTrue(searchViewModel.isLastPage(60))
    }

    @ExperimentalTime
    @Test
    fun `emit response function emits loading and error when server returns error`() = runBlocking {
        fakeSearchRepository.setShouldReturnNetworkError(true)

        val flow = fakeSearchRepository.search(
            "",
            0,
            Rating.G.value,
            Constants.lang
        )

        flow.test {
            assertThat(awaitItem() is State.Loading).isTrue()
            assertThat(awaitItem() is State.Error).isTrue()
            awaitComplete()
        }
    }

    @ExperimentalTime
    @Test
    fun `emit response function emits loading and success when server returns error`() =
        runBlocking {
            fakeSearchRepository.setShouldReturnNetworkError(false)

            val flow = fakeSearchRepository.search(
                "",
                0,
                Rating.G.value,
                Constants.lang
            )

            flow.test {
                assertThat(awaitItem() is State.Loading).isTrue()
                assertThat(awaitItem() is State.Idle).isTrue()
                awaitComplete()
            }
        }

}