package com.klemer.doctorsforeveryone.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.klemer.doctorsforeveryone.R
import com.klemer.doctorsforeveryone.databinding.NewsListItemBinding
import com.klemer.doctorsforeveryone.model.HealthNews

class HealthNewsAdapter(val itemClick: (String) -> Unit) :
    ListAdapter<HealthNews, HealthNewsVH>(HealthNewsDiff()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HealthNewsVH {
        LayoutInflater.from(parent.context)
            .inflate(R.layout.news_list_item, parent, false).apply {
                return HealthNewsVH(this)
            }
    }

    override fun onBindViewHolder(holder: HealthNewsVH, position: Int) {
        getItem(position).apply {
            holder.bind(this)
            holder.itemView.setOnClickListener { itemClick(this.url) }
        }

    }
}

class HealthNewsDiff : DiffUtil.ItemCallback<HealthNews>() {
    override fun areItemsTheSame(oldItem: HealthNews, newItem: HealthNews): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areContentsTheSame(oldItem: HealthNews, newItem: HealthNews): Boolean {
        return oldItem == newItem
    }

}

class HealthNewsVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val binding = NewsListItemBinding.bind(itemView)

    fun bind(news: HealthNews) {
        binding.imgNews.apply {
            Glide.with(this)
                .load(news.urlToImage)
                .placeholder(R.drawable.gradient)
                .into(this)

        }

        binding.tvNewsTitle.text = news.title
    }

}