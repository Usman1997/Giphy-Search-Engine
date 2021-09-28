package com.example.giphysearchengine.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.giphysearchengine.network.entity.Data
import com.example.giphysearchengine.repository.SearchPagingRepository
import com.example.giphysearchengine.utils.Rating
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class SearchPagingViewModel @Inject
constructor(
    private val searchPagingRepository: SearchPagingRepository
) : ViewModel() {

    fun search(param: String, isReset: Boolean = false, rating: String =  Rating.G.value): Flow<PagingData<Data>> {
        return searchPagingRepository.search(param = param,rating = rating, isReset = isReset)
            .map { it }
            .cachedIn(viewModelScope)
    }
}