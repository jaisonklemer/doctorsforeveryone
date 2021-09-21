package com.klemer.doctorsforeveryone.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.klemer.doctorsforeveryone.R
import com.klemer.doctorsforeveryone.databinding.ItemAppointmentBinding
import com.klemer.doctorsforeveryone.model.Appointment

class AppointmentAdapter(private val onCancelClick : (Appointment) -> Unit) : RecyclerView.Adapter<ItemAppointmentViewHolder>() {

    private val listOfAppointment = mutableListOf<Appointment>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemAppointmentViewHolder {
        LayoutInflater.from(parent.context).inflate(R.layout.item_appointment, parent, false)
            .apply {
                return ItemAppointmentViewHolder(this)
            }
    }

    override fun onBindViewHolder(holder: ItemAppointmentViewHolder, position: Int) {
        listOfAppointment[position].apply {
            holder.bind(this, onCancelClick)
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

class ItemAppointmentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val binding = ItemAppointmentBinding.bind(itemView)

    fun bind(appointment: Appointment , onCancelClick: (Appointment) -> Unit) {
        binding.textViewDoctor.text = "Dr. ${appointment.doctor_name}"
        binding.textViewDate.text = appointment.date
        binding.textViewHour.text = appointment.hour.split("-")[0]
        binding.textViewStatus.text = appointment.status
      
        Glide.with(itemView.context)
            .load(appointment.iconDoctor)
            .into(binding.iconSpecialtyDoctor)


        if (appointment.status == "Cancelado") {
            statusColor(R.color.red)
            binding.cancel.visibility = View.GONE
        }
        if (appointment.status == "Agendado") {
            statusColor(R.color.greenLight)
        }
        binding.cancel.setOnClickListener{
            onCancelClick(appointment)
        }
    }

    private fun statusColor(@ColorRes color: Int) {
        binding.colorStatus.setBackgroundColor(itemView.resources.getColor(color))
        binding.colorStatusCircle.setBackgroundColor(itemView.resources.getColor(color))
    }
}