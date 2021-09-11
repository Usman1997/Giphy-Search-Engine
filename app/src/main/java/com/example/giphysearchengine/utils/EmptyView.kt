package com.example.giphysearchengine.utils

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.example.giphysearchengine.R
import com.example.giphysearchengine.databinding.EmptyViewBinding

class EmptyView : ConstraintLayout {
    private val binding = EmptyViewBinding.inflate(LayoutInflater.from(context), this, true)

    val image: ImageView
        get() = binding.image

    val text: TextView
        get() = binding.text

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        context.obtainStyledAttributes(attrs, R.styleable.EmptyView, 0, 0)
            .apply {
                image.setImageResource(getResourceId(R.styleable.EmptyView_image, -1))
                text.text = getString(R.styleable.EmptyView_title)
            }
    }
}