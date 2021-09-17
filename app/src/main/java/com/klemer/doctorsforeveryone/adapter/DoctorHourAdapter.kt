package com.klemer.doctorsforeveryone.adapter

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorStateListDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.klemer.doctorsforeveryone.R
import com.klemer.doctorsforeveryone.databinding.ItensCardsDoctorHoursBinding

class DoctorHourAdapter(private val onClick: (String) -> Unit) :
    ListAdapter<String, DoctorHourVH>(DoctorHourDiff()) {
    private var listOfHours = mutableListOf<String>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorHourVH {
        LayoutInflater.from(parent.context)
            .inflate(R.layout.itens_cards_doctor_hours, parent, false).apply {
                return DoctorHourVH(this)
            }
    }

    override fun onBindViewHolder(holder: DoctorHourVH, position: Int) {
        holder.bind(listOfHours, onClick)
    }

    override fun getItemCount() = 1

    fun update(newList: List<String>) {
        listOfHours.clear()
        listOfHours.addAll(newList)
        notifyDataSetChanged()
    }
}


class DoctorHourDiff : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

}

class DoctorHourVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val binding = ItensCardsDoctorHoursBinding.bind(itemView)

    @SuppressLint("ResourceType")
    fun bind(list: List<String>, onClick: (String) -> Unit) {
        binding.chipGroup.removeAllViews()

        binding.chipGroup.let { chipGroup ->

            list.forEach { hour ->

                Chip(itemView.context).apply {

                    this.text = hour.split("-")[0]
                    isCheckable = true
                    isCheckedIconVisible = false

                    setBackgroundColor(R.color.chip_state_color_list)
                    setOnClickListener {
                        isChecked = true
                        onClick(hour)
                    }
                    chipGroup.addView(this)
                }
            }

        }
    }
}