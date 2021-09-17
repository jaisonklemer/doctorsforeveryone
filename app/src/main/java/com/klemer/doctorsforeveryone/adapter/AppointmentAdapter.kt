package com.klemer.doctorsforeveryone.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.klemer.doctorsforeveryone.R
import com.klemer.doctorsforeveryone.databinding.ItemAppointmentBinding
import com.klemer.doctorsforeveryone.model.Appointment

class AppointmentAdapter: RecyclerView.Adapter<ItemAppointmentViewHolder>() {

    private val listOfAppointment = mutableListOf<Appointment>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemAppointmentViewHolder {
        LayoutInflater.from(parent.context).inflate(R.layout.item_appointment, parent, false).apply {
            return ItemAppointmentViewHolder(this)
        }
    }

    override fun onBindViewHolder(holder: ItemAppointmentViewHolder, position: Int) {
        listOfAppointment[position].apply {
            holder.bind(this)
        }
    }

    override fun getItemCount(): Int {
        return listOfAppointment.size
    }

    fun refresh(newList: List<Appointment>) {
        listOfAppointment.clear()
        listOfAppointment.addAll(newList)
        notifyDataSetChanged()
    }
}

class ItemAppointmentViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    private val binding = ItemAppointmentBinding.bind(itemView)

    fun bind(appointment: Appointment) {
        binding.textViewDoctor.text = "Dr. ${appointment.doctorName}"
        binding.textViewDate.text = appointment.date
        binding.textViewHour.text = appointment.hour.split("-")[0]
        binding.textViewStatus.text = appointment.status
    }
}