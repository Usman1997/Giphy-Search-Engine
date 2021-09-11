package com.example.giphysearchengine.network

import com.example.giphysearchengine.network.entity.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GiphyService {
    @GET("search")
    suspend fun search(
        @Query("api_key") api_key: String,
        @Query("q") q: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
        @Query("rating") rating: String,
        @Query("lang") lang: String,
    ): SearchResponse
}