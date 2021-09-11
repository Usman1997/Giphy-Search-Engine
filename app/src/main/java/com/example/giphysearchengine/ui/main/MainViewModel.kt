package com.example.giphysearchengine.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.giphysearchengine.network.entity.SearchResponse
import com.example.giphysearchengine.repository.SearchRepository
import com.example.giphysearchengine.utils.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject constructor(
    private val searchRepository: SearchRepository
) : ViewModel() {

    private val state = MutableLiveData<State<SearchResponse>>()

    fun search(param: String, offset: Int) {
        viewModelScope.launch {
            searchRepository.search(
                param,
                offset
            ).collect {
                state.value = it
            }
        }
    }

    fun state(): LiveData<State<SearchResponse>> = state
}