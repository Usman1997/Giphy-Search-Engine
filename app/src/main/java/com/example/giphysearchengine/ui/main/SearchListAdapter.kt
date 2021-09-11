package com.example.giphysearchengine.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.giphysearchengine.databinding.SearchListItemBinding
import com.example.giphysearchengine.di.GlideApp
import com.example.giphysearchengine.network.entity.Data

class SearchListAdapter(
    private val onClick: (Data) -> Unit
) : RecyclerView.Adapter<SearchListAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: SearchListItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    private var list = emptyList<Data>()

    fun setData(items: List<Data>) {
        if (items != null)
            list = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            SearchListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.binding) {
            val context = root.context

            val item = list[position]

            GlideApp.with(context)
                .load(item.images.preview_gif.url)
                .into(ivImage)

            root.setOnClickListener {
                onClick(item)
            }
        }
    }
}