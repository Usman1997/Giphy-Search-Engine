/*
 Created by Usman Siddiqui
 */

package com.example.giphysearchengine.ui.search

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.AbsListView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.giphysearchengine.R
import com.example.giphysearchengine.databinding.SearchFragmentBinding
import com.example.giphysearchengine.network.errors.toLocalizedMessage
import com.example.giphysearchengine.ui.base.BaseFragment
import com.example.giphysearchengine.ui.base.viewBinding
import com.example.giphysearchengine.utils.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : BaseFragment<SearchFragmentBinding>(R.layout.search_fragment) {

    private val viewModel: SearchViewModel by viewModels()

    override val binding by viewBinding(SearchFragmentBinding::bind)

    private lateinit var listAdapter: SearchListAdapter

    //For Pagination
    private var currentPage = 0
    private var isError = false
    private var isLoading = false
    private var isScrolling = false
    private var isLastPage = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView()

        binding.apply {

            evSearch.initTextChangeListener(lifecycleScope) {
                if (it.isNotEmpty()) {
                    reset()
                    viewModel.reset()
                    viewModel.search(it, currentPage * Constants.QUERY_PER_PAGE)
                }
            }


            evSearch.showKeyboard()

            viewModel.state().observe(viewLifecycleOwner) { state ->
                when (state) {
                    is State.Loading -> loading.isVisible = true

                    is State.Error -> {
                        /**
                         * Here you can check the exception type by
                         * state.error is ApiException -> and handle your exception accordingly
                         */
                        loading.isVisible = false
                        root.showSnackBar(state.error.toLocalizedMessage(resources))
                    }

                    is State.Idle -> {
                        loading.isVisible = false
                        currentPage++
                        state.data?.let {
                            isLastPage = isLastPage(it.pagination.total_count)
                            listAdapter.differ.submitList(it.data.toList())
                        }
                    }
                }
            }
        }
    }

    private fun setUpRecyclerView() {
        listAdapter = SearchListAdapter(
            onClick = { data, imageView ->

                val directions =
                    SearchFragmentDirections.showDetailFragment(data.images.preview_gif.url)

                val extras = FragmentNavigatorExtras(
                    imageView to data.images.preview_gif.url)

                findNavController().navigate(directions, extras)

            }
        )

        binding.apply {
            with(list) {
                layoutManager = GridLayoutManager(context, 2)
                adapter = listAdapter
                postponeEnterTransition()
                viewTreeObserver.addOnPreDrawListener {
                    startPostponedEnterTransition()
                    true
                }

                addOnScrollListener(this@SearchFragment.scrollListener)
            }
        }
    }

    private val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isNoErrors = !isError
            val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisibleItemPosition >= 0
            val isTotalMoreThanVisible = totalItemCount >= Constants.QUERY_PER_PAGE
            val shouldPaginate =
                isNoErrors && isNotLoadingAndNotLastPage && isAtLastItem && isNotAtBeginning &&
                        isTotalMoreThanVisible && isScrolling
            if (shouldPaginate) {
                binding.apply {
                    viewModel.search(
                        evSearch.text.toString(),
                        currentPage * Constants.QUERY_PER_PAGE
                    )
                }
                isScrolling = false
            }
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                binding.apply {
                    evSearch.hideKeyboard()
                }
                isScrolling = true
            }
        }
    }

    private fun isLastPage(totalCount: Int): Boolean {
        var totalPage = totalCount / Constants.QUERY_PER_PAGE
        if (totalCount % Constants.QUERY_PER_PAGE != 0) {
            totalPage++
        }
        return currentPage == totalPage
    }

    private fun reset() {
        currentPage = 0
        isLastPage = false
    }
}