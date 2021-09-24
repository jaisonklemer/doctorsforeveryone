package com.klemer.doctorsforeveryone.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.klemer.doctorsforeveryone.R
import com.klemer.doctorsforeveryone.databinding.ItensCardsSpecialtyBinding
import com.klemer.doctorsforeveryone.model.Category

class CategoryAdapter(private val onClick: (Category) -> Unit) :
    RecyclerView.Adapter<ItemCategoryViewHolder>() {

    private val listOfCategory = mutableListOf<Category>()
    private var itemCheck = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemCategoryViewHolder {
        LayoutInflater.from(parent.context).inflate(R.layout.itens_cards_specialty, parent, false)
            .apply {
                return ItemCategoryViewHolder(this)
            }
    }

    override fun onBindViewHolder(holder: ItemCategoryViewHolder, @SuppressLint("RecyclerView") position: Int) {
        listOfCategory[position].apply {

            holder.bind(this,itemCheck,position)
            holder.itemView.setOnClickListener {
                itemCheck = position
                onClick(this)
                notifyDataSetChanged()
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

class ItemCategoryViewHolder(itemView: View) :
    RecyclerView.ViewHolder(itemView) {
    private val binding = ItensCardsSpecialtyBinding.bind(itemView)

    @SuppressLint("ResourceType")
    fun bind(category: Category, position: Int, itemCheck : Int) {
        binding.textSpecialty.text = category.name
        Glide.with(itemView.context)
            .load(category.image)
            .into(binding.iconSpecialty)

        if(itemCheck == position) {
            binding.backgroundCard.setBackgroundDrawable(itemView.resources.getDrawable(R.drawable.gradient))
            binding.textSpecialty.setTextColor(itemView.resources.getColor(R.color.greenLight))
        }
        else{
            binding.backgroundCard.setBackgroundColor(itemView.resources.getColor(R.color.gray))
            binding.textSpecialty.setTextColor(itemView.resources.getColor(R.color.gray))
        }
    }
}