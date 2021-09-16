package com.klemer.doctorsforeveryone.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.klemer.doctorsforeveryone.R
import com.klemer.doctorsforeveryone.databinding.ItensCardsDoctorHoursBinding

class DoctorHourAdapter : ListAdapter<String, DoctorHourVH>(DoctorHourDiff()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorHourVH {
        LayoutInflater.from(parent.context)
            .inflate(R.layout.itens_cards_doctor_hours, parent, false).apply {
                return DoctorHourVH(this)
            }
    }

    override fun onBindViewHolder(holder: DoctorHourVH, position: Int) {
        getItem(position).apply {
            holder.bind(this)
        }
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

    fun bind(hour: String) {
        binding.btnHour.text = hour.split("-")[0]
    }
}