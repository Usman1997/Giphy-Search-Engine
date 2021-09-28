/*
 Created by Usman Siddiqui
 */

package com.example.giphysearchengine.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.ViewCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.giphysearchengine.R
import com.example.giphysearchengine.databinding.SearchListItemBinding
import com.example.giphysearchengine.di.GlideApp
import com.example.giphysearchengine.network.entity.Data

class SearchListAdapter(
    private val onClick: (Data, ImageView) -> Unit
) : PagingDataAdapter<Data, SearchListAdapter.ViewHolder>(SearchDiffCallback) {

    inner class ViewHolder(val binding: SearchListItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            SearchListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.binding) {
            val context = root.context

            val item = getItem(position)

            GlideApp.with(context)
                .load(item?.images?.preview_gif?.url)
                .centerCrop()
                .placeholder(R.drawable.bg_placeholder)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(ivImage)

            ViewCompat.setTransitionName(ivImage, item?.images?.preview_gif?.url)

            root.setOnClickListener {
                onClick(item!!, ivImage)
            }
        }
    }
}

object SearchDiffCallback : DiffUtil.ItemCallback<Data>() {
    override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
        return oldItem == newItem
    }
}