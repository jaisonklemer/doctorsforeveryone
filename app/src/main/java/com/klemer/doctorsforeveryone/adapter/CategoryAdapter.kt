package com.klemer.doctorsforeveryone.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.klemer.doctorsforeveryone.R
import com.klemer.doctorsforeveryone.databinding.ItensCardsSpecialtyBinding
import com.klemer.doctorsforeveryone.model.Category

class CategoryAdapter(private val onClick: (Category) -> Unit): RecyclerView.Adapter<ItemCategoryViewHolder>() {

    private val listOfCategory = mutableListOf<Category>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemCategoryViewHolder {
        LayoutInflater.from(parent.context).inflate(R.layout.itens_cards_specialty, parent, false).apply {
            return ItemCategoryViewHolder(this)
        }
    }

    override fun onBindViewHolder(holder: ItemCategoryViewHolder, position: Int) {
        listOfCategory[position].apply {
            holder.bind(this)
            holder.itemView.setOnClickListener {
                onClick(this)
            }
        }
    }

    override fun getItemCount(): Int {
        return listOfCategory.size
    }

    fun refresh(newList: List<Category>) {
        listOfCategory.clear()
        listOfCategory.addAll(newList)
        notifyDataSetChanged()
    }
}

class ItemCategoryViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    private val binding = ItensCardsSpecialtyBinding.bind(itemView)

    fun bind(category: Category) {
        binding.textSpecialty.text = category.name
        Glide.with(itemView.context)
            .load(category.image)
            .into(binding.iconSpecialty)
    }
}