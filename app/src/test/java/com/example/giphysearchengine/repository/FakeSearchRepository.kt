/*
 Created by Usman Siddiqui
 */
package com.example.giphysearchengine.repository

import com.example.giphysearchengine.network.entity.Meta
import com.example.giphysearchengine.network.entity.Pagination
import com.example.giphysearchengine.network.entity.SearchResponse
import com.example.giphysearchengine.utils.State
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException


class FakeSearchRepository : SearchRepository {

    private var shouldReturnNetworkError = false

    override suspend fun search(
        param: String,
        offset: Int,
        rating: String
    ): Flow<State<SearchResponse>> {
        return if (shouldReturnNetworkError) flowWithErrorState()
        else flowWithIdleState()

    }

    /**
     * This function is to set bool variable to return success or error for ViewModel test cases
     */
    fun setShouldReturnNetworkError(value: Boolean) {
        shouldReturnNetworkError = value
    }

    /**
     * This function simulates the behavior when the API fails and returns
     * Exception
     */
    private fun flowWithErrorState(): Flow<State<SearchResponse>> =
        flow {
            emit(State.loading())
            emit(State.error(IOException()))
        }

    /**
     * This function simulates the behavior when the API runs successfully and returns
     * SearchResponse Object
     */
    private fun flowWithIdleState(): Flow<State<SearchResponse>> {
        val searchResponse = SearchResponse(
            mutableListOf(),
            Meta("success", "1", 200),
            Pagination(20, 0, 64)
        )

        return flow {
            emit(State.loading())
            emit(State.idle(searchResponse))
        }
    }

}

