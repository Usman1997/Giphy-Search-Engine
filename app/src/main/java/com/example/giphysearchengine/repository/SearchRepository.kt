package com.example.giphysearchengine.repository

import com.example.giphysearchengine.BuildConfig
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
        rating: String = Rating.G.value,
        lang: String = Constants.lang
    ): Flow<State<SearchResponse>>
}

class SearchRepositoryImpl(
    private val giphyService: GiphyService
) : SearchRepository {
    override suspend fun search(param: String, offset: Int, rating: String, lang: String) =
        flow {
            emit(
                giphyService.search(
                    param,
                    Constants.QUERY_PER_PAGE,
                    offset,
                    rating,
                    lang
                )
            )
        }
            .map { State.idle(it) }
            .catch { emit(State.error(it)) }
            .onStart { emit(State.loading()) }


}
