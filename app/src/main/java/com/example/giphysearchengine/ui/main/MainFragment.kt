package com.example.giphysearchengine.ui.main

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.giphysearchengine.R
import com.example.giphysearchengine.databinding.MainFragmentBinding
import com.example.giphysearchengine.ui.base.BaseFragment
import com.example.giphysearchengine.ui.base.viewBinding
import com.example.giphysearchengine.utils.*
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : BaseFragment<MainFragmentBinding>(R.layout.main_fragment) {

    private val viewModel: MainViewModel by viewModels()

    override val binding by viewBinding(MainFragmentBinding::bind)

    private lateinit var listAdapter: SearchListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listAdapter = SearchListAdapter(
            onClick = {
                findNavController()?.safeNavigate(
                    MainFragmentDirections.showDetailFragment(
                        it.images.original.url
                    )
                )
            }
        )

        binding.apply {

            with(list) {
                layoutManager = GridLayoutManager(context, 2)
                adapter = listAdapter
            }

            evSearch.initTextChangeListener(lifecycleScope) {
//                if (it.length < 3) {
//                    listAdapter.setData(emptyList())
//                    tvNoResult.isVisible = true
//                } else {
//                    viewModel.search(it, 25)
//                }

                if(it.isNotEmpty()){
                    viewModel.search(it, 25)
                }

//                it.isEmpty().apply {
//                    emptyView.isVisible = this
//                    list.isGone = this
//                    if (this)
//                        tvNoResult.isGone = true
//                }
            }

            evSearch.setOnEditorActionListener { _, actionId, event ->
                if (event != null) {
                    if (actionId == EditorInfo.IME_ACTION_DONE)
                        evSearch.hideKeyboard()
                    true
                } else
                    false
            }

            evSearch.showKeyboard()

            viewModel.state().observe(viewLifecycleOwner) { state ->
                when (state) {
                    is State.Loading -> loading.isVisible = true

                    is State.Error -> {
                        loading.isVisible = false
                        root.showSnackBar(state.error.toLocalizedMessage(resources))
                    }

                    is State.Idle -> {
                        loading.isVisible = false
                        //tvNoResult.isVisible = state.data?.data.isNullOrEmpty()

                        state.data?.data?.let {
                            listAdapter.setData(it)
                        }
                    }
                }
            }
        }
    }
}