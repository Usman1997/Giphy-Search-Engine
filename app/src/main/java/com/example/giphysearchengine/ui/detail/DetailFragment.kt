package com.example.giphysearchengine.ui.detail

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import android.widget.ImageView
import androidx.core.view.ViewCompat
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.giphysearchengine.R
import com.example.giphysearchengine.databinding.DetailFragmentBinding
import com.example.giphysearchengine.di.GlideApp
import com.example.giphysearchengine.ui.base.BaseFragment
import com.example.giphysearchengine.ui.base.viewBinding

class DetailFragment : BaseFragment<DetailFragmentBinding>(R.layout.detail_fragment) {

    override val binding by viewBinding(DetailFragmentBinding::bind)

    private val args: DetailFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition()
        binding.apply {

            ViewCompat.setTransitionName(ivImage, args.url)


            //TODO Glide has a bug to load GIF with shared element transition therefore
            // we have to add postponeEnterTransition before setting the GIF and then calling
            // startPostponedTransition in onLoad and onResourceReady. We can maybe remove this
            // when Glide fix this bug in a new version
            // https://stackoverflow.com/questions/55980750/gif-is-not-playing-after-shared-element-transition-glide-v-4-8-0

            GlideApp.with(requireActivity())
                .load(args.url)
                .centerCrop()
                .placeholder(R.drawable.bg_placeholder)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        setStartPostponedTransition(ivImage)
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        setStartPostponedTransition(ivImage)
                        return false
                    }
                })
                .into(ivImage)
        }
    }

    private fun setStartPostponedTransition(image: ImageView) {
        image.viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
            override fun onPreDraw(): Boolean {
                image.viewTreeObserver.removeOnPreDrawListener(this)
                startPostponedEnterTransition()
                return true
            }
        })
    }
}