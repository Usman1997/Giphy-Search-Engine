package com.example.giphysearchengine.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.giphysearchengine.network.GiphyService
import com.example.giphysearchengine.network.entity.Data
import com.example.giphysearchengine.utils.Constants
import com.example.giphysearchengine.utils.Rating
import kotlinx.coroutines.flow.Flow

class SearchPagingRepository(private val giphyService: GiphyService) {

    private fun getDefaultPageConfig(): PagingConfig =
        PagingConfig(
            pageSize = Constants.QUERY_PER_PAGE, enablePlaceholders = false
        )

    fun search(
        pagingConfig: PagingConfig = getDefaultPageConfig(),
        param: String,
        rating: String,
        isReset: Boolean
    ): Flow<PagingData<Data>> {
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = { SearchPagingSource(giphyService,param,rating,isReset) }
        ).flow
    }

}