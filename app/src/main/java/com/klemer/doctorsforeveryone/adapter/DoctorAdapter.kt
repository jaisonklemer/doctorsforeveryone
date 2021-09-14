package com.klemer.doctorsforeveryone.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.klemer.doctorsforeveryone.R
import com.klemer.doctorsforeveryone.databinding.ItensCardsDoctorsBinding
import com.klemer.doctorsforeveryone.model.Doctor

class DoctorAdapter(private val onClick: (Doctor) -> Unit): RecyclerView.Adapter<ItemDoctorViewHolder>() {

    private var listOfDoctor = mutableListOf<Doctor>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemDoctorViewHolder {
        LayoutInflater.from(parent.context).inflate(R.layout.itens_cards_doctors, parent, false).apply {
            return ItemDoctorViewHolder(this)
        }
    }

    override fun onBindViewHolder(holder: ItemDoctorViewHolder, position: Int) {
        listOfDoctor[position].apply {
            holder.bind(this)
            holder.itemView.setOnClickListener {
                onClick(this)
            }
        }
    }

    override fun getItemCount(): Int {
        return listOfDoctor.size
    }

    fun refresh(newList: List<Doctor>) {
        listOfDoctor.clear()
        listOfDoctor.addAll(newList)
        notifyDataSetChanged()
    }
}

class ItemDoctorViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    private var binding = ItensCardsDoctorsBinding.bind(itemView)

    fun bind(doctor: Doctor) {
        binding.nameDoctor.text = doctor.name
        binding.nameDoctorCategory.text = doctor.category
        Glide.with(itemView.context).load(doctor.avatarDoctor).into(binding.avatarDoctor)
        Glide.with(itemView.context).load(doctor.iconDoctorCategory).into(binding.iconSpecialtyDoctor)


    }
}