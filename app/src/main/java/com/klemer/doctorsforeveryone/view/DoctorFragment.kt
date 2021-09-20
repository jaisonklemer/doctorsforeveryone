package com.klemer.doctorsforeveryone.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.klemer.doctorsforeveryone.DoctorActivity
import com.klemer.doctorsforeveryone.R
import com.klemer.doctorsforeveryone.adapter.DoctorHourAdapter
import com.klemer.doctorsforeveryone.databinding.DoctorFragmentBinding
import com.klemer.doctorsforeveryone.model.Doctor
import com.klemer.doctorsforeveryone.view_model.DoctorViewModel
import java.text.SimpleDateFormat
import java.util.*


class DoctorFragment : Fragment(R.layout.doctor_fragment) {

    companion object {
        fun newInstance() = DoctorFragment()
    }

    private lateinit var viewModel: DoctorViewModel
    private lateinit var binding: DoctorFragmentBinding
    private var selectedHour: String? = null
    private var selectedDate: String? = null
    private var selectedDay: String? = null

    private val adapter = DoctorHourAdapter {
        selectedHour = it
    }
    private lateinit var currentDoctor: Doctor

    private val doctorObserver = Observer<Doctor> {
        currentDoctor = it
        bindDoctorInfo(it)
    }

    private val doctorHoursObserver = Observer<List<String>> {
        adapter.update(it)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[DoctorViewModel::class.java]
        binding = DoctorFragmentBinding.bind(view)

        setupObservers()
        getDoctorParams()
        setupRecyclerView()
        setupCalendar()
        setupButtonClick()

    }

    private fun buttonSaveEnable(): Boolean {
        return selectedDate != null && selectedHour != null
    }

    private fun setupButtonClick() {
        binding.btnMakeAppointment.setOnClickListener {
            if (buttonSaveEnable())
                createAppointment()
            else
                Toast.makeText(
                    requireContext(),
                    "Selecione uma data e um horário",
                    Toast.LENGTH_LONG
                )
                    .show()
        }
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

    private fun getDoctorAvailableHours(doctor: Doctor, date: String, selectedDay: String) {
        viewModel.getDoctorHours(doctor, date, selectedDay)
    }

    @SuppressLint("SimpleDateFormat")
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
        val datePicker = binding.datePicker

        datePicker.getSelectedDate { date ->
            if (date != null) {
                if (checkIfWeekendDay(date)) {
                    binding.rvDoctorHours.visibility = View.VISIBLE
                    binding.imgNotWorking.visibility = View.GONE
                    selectedDate = formatDate(date)
                    selectedDay = selectedDate?.split("/")?.get(0)
                    getDoctorAvailableHours(currentDoctor, selectedDate!!, selectedDay!!)
                } else {
                    binding.rvDoctorHours.visibility = View.GONE
                    binding.imgNotWorking.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun checkIfWeekendDay(date: Date): Boolean {
        with(date.day + 1) {
            return this != Calendar.SATURDAY && this != Calendar.SUNDAY
        }
    }

    private fun createAppointment() {
        if (selectedDate != null && selectedHour != null) {
            viewModel.insertUserAppointment(currentDoctor, selectedDate!!, selectedHour!!)
            runActivityResult()
        }
    }

    private fun runActivityResult() {
        Intent().let { intent ->
            intent.putExtra("created_appointment", true)
            (requireActivity() as DoctorActivity?)?.apply {
                setResult(0, intent)
                finish()
            }
        }
    }

    private fun bindDoctorInfo(doctor: Doctor) {
        binding.imgDoctorAvatar.apply {
            Glide.with(this).load(doctor.avatarDoctor).into(this)
        }
        binding.imgDoctorCategory.apply {
            Glide.with(this).load(doctor.iconDoctorCategory).into(this)
        }

        binding.tvDoctorName.text = doctor.name
        binding.tvDoctorCategory.text = doctor.category
        binding.tvDoctorDescription.text = doctor.biography
    }

//    private fun checkListOfHours(listOfHours: List<String>) {
//        if (selectedDay == getCurrentDay()) {
//            val finalList = mutableListOf<String>()
//            listOfHours.forEach {
//                val hour = it.split(":")[0]
//                if (hour.toInt() > getCurrentHour().toInt()) {
//                    finalList.add(it)
//                }
//            }
//            adapter.update(finalList)
//        } else {
//            adapter.update(listOfHours)
//        }
//
//        println(getCurrentDay())
//    }
}


