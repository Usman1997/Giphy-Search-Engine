/*
 Created by Usman Siddiqui
 */

package com.example.giphysearchengine.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.giphysearchengine.network.entity.SearchResponse
import com.example.giphysearchengine.repository.SearchRepository
import com.example.giphysearchengine.utils.Constants
import com.example.giphysearchengine.utils.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel
@Inject constructor(
    private val searchRepository: SearchRepository
) : ViewModel() {

    private val state = MutableLiveData<State<SearchResponse>>()
    private var searchResponse: SearchResponse? = null
    private var currentPage = 0

    /**
     * This function will communicate with the repository to fetch data from
     * server
     */
    fun search(param: String) {
        viewModelScope.launch {
            searchRepository.search(
                param,
                currentPage * Constants.QUERY_PER_PAGE
            ).collect {
                emitResponse(it)
            }
        }
    }

    /**
     * This function will emit response to UI according to the current state
     */
    private fun emitResponse(responseState: State<SearchResponse>) = when (responseState) {
        is State.Loading -> state.postValue(State.loading())
        is State.Error -> state.postValue(State.error(responseState.error))
        is State.Idle -> state.postValue(handleResponse(responseState.data))
    }

    /**
     * We can not directly update the mutable live data because if the mutable live data only holds
     * the current state of data then it will not be able to recover all the previous data in case
     * of configuration changes. For example, the user is on page 3 and the user rotate the device then
     * we will only have the data of page 3 if we directly update the data state. In order to avoid this
     * we have this function which will keep the data of all pages and pass it adapter which will find
     * the new items using diff utils.
     */
    private fun handleResponse(response: SearchResponse?): State<SearchResponse> {
        response?.let {

            setCurrentPage(currentPage+1)

            if (searchResponse == null) {
                searchResponse = response
            } else {
                val oldData = searchResponse?.data
                val newData = response.data
                oldData?.addAll(newData)
            }
        }
        return State.idle(searchResponse)
    }

    /**
     * This function will calculate the total number of pages and return true if the
     * current page is last page
     */
    fun isLastPage(totalCount: Int): Boolean {
        var totalPage = totalCount / Constants.QUERY_PER_PAGE
        if (totalCount % Constants.QUERY_PER_PAGE != 0) {
            totalPage++
        }
        return currentPage == totalPage
    }

    /**
     * This function is to reset the page and search response when the user search new keywords
     * so that the old data will be removed and new data is shown to the user
     */
    fun reset() {
        setCurrentPage(0)
        searchResponse = null
    }

    /**
     * We can directly update the current page anywhere in this class but the reason to use
     * this function is to have an ability to set current page from the test class and test
     * isLastPage method properly. I can directly update the variable but that is not a good practice.
     * The variable currentPage should be private to this class and can only be updated through setter
     * method
     */
    fun setCurrentPage(value: Int) {
        currentPage = value
    }

    /**
     * This function is to expose live data to UI. The UI should only be able to
     * read live data therefore it should not have to access to mutable live data
     */
    fun state(): LiveData<State<SearchResponse>> = state
}