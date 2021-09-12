/*
 Created by Usman Siddiqui
 */

package com.example.giphysearchengine.network.entity

data class SearchResponse(
    val data: MutableList<Data>,
    val meta: Meta,
    val pagination: Pagination
)