package com.klemer.doctorsforeveryone.view

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.graphics.Paint
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.google.android.material.textfield.TextInputEditText
import com.klemer.doctorsforeveryone.R
import com.klemer.doctorsforeveryone.adapter.DoctorHourAdapter
import com.klemer.doctorsforeveryone.databinding.DoctorFragmentBinding
import com.klemer.doctorsforeveryone.model.Doctor
import com.klemer.doctorsforeveryone.view_model.DoctorViewModel
import java.text.SimpleDateFormat
import java.util.*
import com.harrywhewell.scrolldatepicker.OnDateSelectedListener


class DoctorFragment : Fragment(R.layout.doctor_fragment) {

    companion object {
        fun newInstance() = DoctorFragment()
    }

    private lateinit var viewModel: DoctorViewModel
    private lateinit var binding: DoctorFragmentBinding
    private val adapter = DoctorHourAdapter()
    private lateinit var currentDoctor: Doctor

    private val doctorObserver = Observer<Doctor> {
        currentDoctor = it
    }

    private val doctorHoursObserver = Observer<List<String>> {
        adapter.submitList(it)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[DoctorViewModel::class.java]
        binding = DoctorFragmentBinding.bind(view)

        setupObservers()
        getDoctorParams()
        setupRecyclerView()
        setupCalendar()

    }


    private fun setupObservers() {
        viewModel.doctor.observe(viewLifecycleOwner, doctorObserver)
        viewModel.doctorHours.observe(viewLifecycleOwner, doctorHoursObserver)
    }

    private fun getDoctorParams() {

        val doctorId = arguments?.getString("doctor_id")

        if (doctorId != null) {
            viewModel.getDoctorById(doctorId)
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun getDoctorAvailableHours(doctor: Doctor, date: String) {
        viewModel.getDoctorHours(doctor, date)
    }

    private fun formatDate(date: Date): String {
        val sdf = SimpleDateFormat("dd/MM/yyyy")
        return sdf.format(date)
    }

    private fun setupRecyclerView() {

        binding.rvDoctorHours.layoutManager = FlexboxLayoutManager(requireContext()).apply {
            alignItems = AlignItems.CENTER
            justifyContent = JustifyContent.CENTER
        }
        binding.rvDoctorHours.adapter = adapter

    }

    private fun setupCalendar() {
        val datePicker = binding.datepicker
        datePicker.getSelectedDate { date ->
            if (date != null) {
                getDoctorAvailableHours(currentDoctor, formatDate(date))
                println(formatDate(date))
            }
        }

    }

    @SuppressLint("SimpleDateFormat")
    private fun updateDateInView() {
        val format = SimpleDateFormat("dd/MM/yyyy", Locale("pt-BR"))
//        binding.textInputDate.setText(format.format(calendar.time))
    }
}


