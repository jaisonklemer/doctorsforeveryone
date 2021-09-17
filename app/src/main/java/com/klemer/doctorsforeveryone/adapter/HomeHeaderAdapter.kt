package com.klemer.doctorsforeveryone.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.klemer.doctorsforeveryone.R
import com.klemer.doctorsforeveryone.databinding.HomeHeaderBinding

class HomeHeaderAdapter(private val searchAdapter: HeaderAdapter) : RecyclerView.Adapter<HomeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        LayoutInflater.from(parent.context).inflate(R.layout.home_header, parent, false).apply {
                return HomeViewHolder(this, searchAdapter)

            }
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int = 1

}

class HomeViewHolder(itemView: View, searchAdapter: HeaderAdapter) : RecyclerView.ViewHolder(itemView) {

    private val binding: HomeHeaderBinding = HomeHeaderBinding.bind(itemView)
    val adapterSearch = searchAdapter
    fun bind() {
        binding.recyclerViewSearch.adapter = adapterSearch
        binding.recyclerViewSearch.layoutManager = LinearLayoutManager(itemView.context)

    }

}
