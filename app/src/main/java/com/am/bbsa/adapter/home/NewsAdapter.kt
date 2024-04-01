package com.am.bbsa.adapter.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.am.bbsa.data.response.DataItemNews
import com.am.bbsa.databinding.ItemContentNewsBinding
import com.bumptech.glide.Glide

class NewsAdapter : ListAdapter<DataItemNews, NewsAdapter.ViewHolder>(DIFF_CALLBACK) {
    var callbackOnclick: ((DataItemNews) -> Unit)? = null

    inner class ViewHolder(private val binding: ItemContentNewsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(dataNews: DataItemNews) {
            binding.textTitle.text = dataNews.title
            binding.textDesc.text = dataNews.description
            Glide.with(binding.root.context).load(dataNews.photo).into(binding.imageNews)
            binding.root.setOnClickListener {
                callbackOnclick?.invoke(dataNews)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemContentNewsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataNews = getItem(position)
        holder.bind(dataNews)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataItemNews>() {
            override fun areItemsTheSame(
                oldItem: DataItemNews, newItem: DataItemNews
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: DataItemNews, newItem: DataItemNews
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}