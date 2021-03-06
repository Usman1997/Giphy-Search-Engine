/*
 Created by Usman Siddiqui
 */

package com.example.giphysearchengine.repository

import com.example.giphysearchengine.network.GiphyService
import com.example.giphysearchengine.network.entity.SearchResponse
import com.example.giphysearchengine.utils.Constants
import com.example.giphysearchengine.utils.Rating
import com.example.giphysearchengine.utils.State
import kotlinx.coroutines.flow.*

interface SearchRepository {

    suspend fun search(
        param: String,
        offset: Int,
        rating: String = Rating.G.value
    ): Flow<State<SearchResponse>>
}

class SearchRepositoryImpl(
    private val giphyService: GiphyService
) : SearchRepository {

    /**
     * This function will communicate with server to fetch data from API
     */
    override suspend fun search(param: String, offset: Int, rating: String) =
        flow {
            emit(
                giphyService.search(
                    param,
                    Constants.QUERY_PER_PAGE,
                    offset,
                    rating
                )
            )
        }
            .map { State.success(it) }
            .catch { emit(State.error(it)) }
            .onStart { emit(State.loading()) }


}
