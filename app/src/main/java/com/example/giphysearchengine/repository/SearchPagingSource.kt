package com.example.giphysearchengine.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.giphysearchengine.network.GiphyService
import com.example.giphysearchengine.network.entity.Data
import com.example.giphysearchengine.utils.Constants
import retrofit2.HttpException
import java.io.IOException

class SearchPagingSource(
    private val giphyService: GiphyService,
    private val param: String,
    private val rating: String,
    private val isReset: Boolean
) :
    PagingSource<Int, Data>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Data> {
        val offset =  params.key ?: 0

        return try {
            val response = giphyService.search(param, Constants.QUERY_PER_PAGE, offset, rating)
            val pagination = response.pagination
            val data = response.data

            LoadResult.Page(
                data,
                prevKey = null,
                nextKey = if (isLastPage(
                        pagination.total_count,
                        pagination.offset
                    )
                ) null else pagination.offset + Constants.QUERY_PER_PAGE

            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }

    }

    override fun getRefreshKey(state: PagingState<Int, Data>): Int = 0


    fun isLastPage(totalCount: Int, currentOffset: Int) =
        currentOffset + Constants.QUERY_PER_PAGE >= totalCount


}