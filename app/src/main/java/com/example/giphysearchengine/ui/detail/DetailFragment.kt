package com.example.giphysearchengine.ui.detail

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.example.giphysearchengine.R
import com.example.giphysearchengine.databinding.DetailFragmentBinding
import com.example.giphysearchengine.di.GlideApp
import com.example.giphysearchengine.ui.base.BaseFragment
import com.example.giphysearchengine.ui.base.viewBinding

class DetailFragment : BaseFragment<DetailFragmentBinding>(R.layout.detail_fragment) {

    override val binding by viewBinding(DetailFragmentBinding::bind)

    private val args: DetailFragmentArgs by navArgs()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            GlideApp.with(requireActivity())
                .load(args.url)
                .into(ivImage)
        }
    }
}