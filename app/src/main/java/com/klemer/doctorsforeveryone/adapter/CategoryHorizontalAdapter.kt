package com.klemer.doctorsforeveryone.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.klemer.doctorsforeveryone.R
import com.klemer.doctorsforeveryone.databinding.CategoryListBinding

class CategoryHorizontalAdapter(private val categoryAdapter: CategoryAdapter): RecyclerView.Adapter<CategoryHorizontalViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryHorizontalViewHolder {
        LayoutInflater.from(parent.context).inflate(R.layout.category_list,parent,false).apply {
            return CategoryHorizontalViewHolder(this, categoryAdapter)
        }
    }

    override fun onBindViewHolder(holder: CategoryHorizontalViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int = 1
}

class CategoryHorizontalViewHolder(itemView : View, private val categoryAdapter: CategoryAdapter) : RecyclerView.ViewHolder(itemView) {

    private val binding  = CategoryListBinding.bind(itemView)
    val adapterCategory = categoryAdapter

    fun bind(){
        binding.recyclerViewCategory.adapter = adapterCategory
        binding.recyclerViewCategory.layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
    }
}