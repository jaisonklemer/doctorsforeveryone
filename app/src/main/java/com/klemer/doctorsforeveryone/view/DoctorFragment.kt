package com.klemer.doctorsforeveryone.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
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
import com.klemer.doctorsforeveryone.model.Appointment
import com.klemer.doctorsforeveryone.model.Doctor
import com.klemer.doctorsforeveryone.utils.checkForInternet
import com.klemer.doctorsforeveryone.utils.formatDate
import com.klemer.doctorsforeveryone.utils.showAlertDialog
import com.klemer.doctorsforeveryone.view_model.DoctorViewModel
import com.klemer.doctorsforeveryone.view_model.SchedulesViewModel
import java.util.*


class DoctorFragment : Fragment(R.layout.doctor_fragment) {

    companion object {
        fun newInstance() = DoctorFragment()
    }

    private lateinit var viewModel: DoctorViewModel
    private lateinit var appointmentViewModel: SchedulesViewModel
    private lateinit var binding: DoctorFragmentBinding
    private var selectedHour: String? = null
    private var selectedDate: String? = null

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
        if (it.isEmpty()){
            binding.imgNotWorkingInDate.visibility = View.VISIBLE
        }else{
            binding.imgNotWorkingInDate.visibility = View.GONE
        }
    }

    private val appointmentInsertedObserver = Observer<Boolean> {
        if (it) {
            showProgressBar(false)
            showAlertDialog(
                requireContext(),
                getString(R.string.completed),
                getString(R.string.appointment_success_label),
                getString(R.string.understand_label),
                null,
                null
            ) { positive, _ ->
                if (positive) {
                    runActivityResult()
                }
            }
        }
    }

    private val userAppointmentObserver = Observer<List<Appointment>> {
        if (it.isNullOrEmpty()) {
            createAppointment()
        } else {
            showProgressBar(false)
            showAlertDialog(
                requireContext(),
                getString(R.string.warning),
                getString(R.string.appointment_warning_alert),
                getString(R.string.understand_label),
                null,
                null
            ) { _, _ -> }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[DoctorViewModel::class.java]
        appointmentViewModel = ViewModelProvider(this)[SchedulesViewModel::class.java]

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
                checkIfCanCreateAppointment()
            else
                Toast.makeText(
                    requireContext(),
                    getString(R.string.select_hour_and_date_warning),
                    Toast.LENGTH_LONG
                )
                    .show()
        }
    }

    private fun setupObservers() {
        viewModel.doctor.observe(viewLifecycleOwner, doctorObserver)
        viewModel.doctorHours.observe(viewLifecycleOwner, doctorHoursObserver)
        viewModel.appointmentInserted.observe(viewLifecycleOwner, appointmentInsertedObserver)
        appointmentViewModel.appointmentUser.observe(viewLifecycleOwner, userAppointmentObserver)
    }

    private fun getDoctorParams() {

        val doctorId = arguments?.getString("doctor_id")

        if (doctorId != null) {
            viewModel.getDoctorById(doctorId)
            showProgressBar(true)
        }
    }

    private fun getDoctorAvailableHours(doctor: Doctor, date: String) {
        viewModel.getDoctorHours(doctor, date)
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
                    getDoctorAvailableHours(currentDoctor, selectedDate!!)
                } else {
                    binding.rvDoctorHours.visibility = View.GONE
                    binding.imgNotWorking.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun afterDatePickerSetup() {
        val datePicker = binding.datePicker
        val currentDate = org.joda.time.LocalDate(formatDate(Date(), "yyyy-MM-dd"))

        datePicker.findViewById<TextView>(R.id.date_picker_scroll_day_month).apply {
            text = currentDate.toString("MMMM")
        }

    }

    private fun checkIfWeekendDay(date: Date): Boolean {
        with(date.day + 1) {
            return this != Calendar.SATURDAY && this != Calendar.SUNDAY
        }
    }

    private fun checkIfCanCreateAppointment() {
        appointmentViewModel.fetchAppointmentByStatusAndDate(
            "Agendado",
            selectedDate!!,
            selectedHour!!
        )
        showProgressBar(true)
    }

    private fun createAppointment() {
        if (selectedDate != null && selectedHour != null && checkForInternet(requireContext())) {
            showProgressBar(true)
            viewModel.insertUserAppointment(currentDoctor, selectedDate!!, selectedHour!!)
        } else {
            showProgressBar(false)
            showAlertDialog(
                requireContext(),
                getString(R.string.warning),
                getString(R.string.no_connection),
                getString(R.string.understand_label),
                null,
                null
            ) { _, _ -> }
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
        showProgressBar(false)
        binding.imgDoctorAvatar.apply {
            Glide.with(this).load(doctor.avatarDoctor).into(this)
        }
        binding.imgDoctorCategory.apply {
            Glide.with(this).load(doctor.iconDoctorCategory).into(this)
        }

        binding.tvDoctorName.text = doctor.name
        binding.tvDoctorCategory.text = doctor.category
        binding.tvDoctorDescription.text = doctor.biography

        afterDatePickerSetup()
    }

    private fun showProgressBar(show: Boolean) {
        binding.lottieAnimationView.visibility = if (show) View.VISIBLE else View.GONE
    }

}


