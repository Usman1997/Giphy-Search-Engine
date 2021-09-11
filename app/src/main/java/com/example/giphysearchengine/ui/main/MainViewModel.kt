package com.example.giphysearchengine.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.giphysearchengine.network.entity.SearchResponse
import com.example.giphysearchengine.repository.SearchRepository
import com.example.giphysearchengine.utils.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject constructor(
    private val searchRepository: SearchRepository
) : ViewModel() {

    private val state = MutableLiveData<State<SearchResponse>>()
    private var searchResponse: SearchResponse? = null

    fun search(param: String, offset: Int) {
        viewModelScope.launch {
            searchRepository.search(
                param,
                offset
            ).collect {
                emitResponse(it)
            }
        }
    }

    private fun emitResponse(responseState: State<SearchResponse>) = when (responseState) {
        is State.Loading -> state.postValue(State.loading())
        is State.Error -> state.postValue(State.error(responseState.error))
        is State.Idle -> state.postValue(handleResponse(responseState.data))
    }


    private fun handleResponse(response: SearchResponse?): State<SearchResponse> {
        response?.let {
            if (searchResponse == null) {
                searchResponse = response
            } else {
                val oldData = searchResponse?.data
                val newData = response.data
                oldData?.addAll(newData)
            }
            return State.idle(searchResponse)
        }
        return State.idle(searchResponse ?: response)
    }

    fun reset(){
        searchResponse = null
    }

    fun state(): LiveData<State<SearchResponse>> = state
}