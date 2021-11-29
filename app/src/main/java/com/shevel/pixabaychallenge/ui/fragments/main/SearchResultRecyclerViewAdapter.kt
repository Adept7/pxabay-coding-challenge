package com.shevel.pixabaychallenge.ui.fragments.main

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shevel.pixabaychallenge.R
import com.shevel.pixabaychallenge.databinding.ItemSearchResultBinding
import com.shevel.pixabaychallenge.model.ImageData
import com.shevel.pixabaychallenge.utils.tagsFormatted
import com.shevel.pixabaychallenge.utils.usernameFormatted


class SearchResultRecyclerViewAdapter constructor(
    private val clickListener: (ImageData) -> Unit
) : RecyclerView.Adapter<SearchResultRecyclerViewAdapter.ViewHolder>() {

    private var values: ArrayList<ImageData> = arrayListOf()

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(data: List<ImageData>) {
        values.clear()
        values.addAll(data)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = values.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemSearchResultBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(values[position])
    }

    inner class ViewHolder(private val binding: ItemSearchResultBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindData(data: ImageData) {
            val url = data.imageURL ?: data.largeImageURL
            Glide.with(binding.root)
                .load(url)
                .thumbnail(0.3f)
                .placeholder(R.drawable.ic_placeholder)
                .into(binding.image)
            binding.username.text = usernameFormatted(binding.root.resources, data.user)
            binding.tags.text = tagsFormatted(data.tags)
            binding.root.setOnClickListener { clickListener.invoke(data) }
        }
    }
}