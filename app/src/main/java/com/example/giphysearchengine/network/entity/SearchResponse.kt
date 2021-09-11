package com.example.giphysearchengine.network.entity

data class SearchResponse(
    val data: List<Data>,
    val meta: Meta,
    val pagination: Pagination
)