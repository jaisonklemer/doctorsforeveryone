package com.klemer.doctorsforeveryone.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.klemer.doctorsforeveryone.model.Appointment
import com.klemer.doctorsforeveryone.model.Doctor
import com.klemer.doctorsforeveryone.repository.AppointmentRepository
import com.klemer.doctorsforeveryone.repository.DoctorRepository
import com.klemer.doctorsforeveryone.utils.getCurrentDate
import com.klemer.doctorsforeveryone.utils.getCurrentDay
import com.klemer.doctorsforeveryone.utils.getCurrentHour
import kotlinx.coroutines.launch

class DoctorViewModel : ViewModel() {

    private val repository = DoctorRepository()
    private val appointmentRepository = AppointmentRepository()

    var doctorGet = MutableLiveData<List<Doctor>>()
    var doctor = MutableLiveData<Doctor>()
    var doctorInsert = MutableLiveData<Boolean>()
    var error = MutableLiveData<String>()
    var doctorHours = MutableLiveData<List<String>>()

    fun fetchDoctor() {
        repository.getAllDoctors { listDoc, e ->
            doctorGet.value = listDoc
            error.value = e
        }
    }

    fun fetchDoctorByCategory(name: String) {
        repository.getDoctorByCategory(name) { listDoc, e ->
            doctorGet.value = listDoc
            error.value = e
        }
    }

    fun fetchDoctorByName(nameDoc: String) {
        repository.getDoctorByName(nameDoc) { listDoc, e ->
            doctorGet.value = listDoc
            error.value = e
        }
    }

    fun insertDoctor(doctor: Doctor) {
        repository.insertDoctor(doctor) { insertDoctor, e ->
            doctorInsert.value = insertDoctor
            error.value = e
        }
    }

    fun getDoctorById(doctorId: String) {
        repository.getDoctorById(doctorId = doctorId) { doctorResult, err ->
            if (doctorResult != null)
                doctor.value = doctorResult

            if (err != null)
                error.value = err
        }
    }

    fun getDoctorHours(doctor: Doctor, date: String) {
        val currentDoctorHours = doctor.calculateWorkingHours()
        val availableHours = mutableListOf<String>()

        appointmentRepository.getAppointmentsByDoctor(
            doctorId = doctor.id!!,
            date = date
        ) { appointments, _ ->
            appointments?.forEach { appointment ->
                availableHours.add(appointment.hour)
            }

            val totalDoctorHours =
                currentDoctorHours.filter { x -> !availableHours.contains(x) }
                        as MutableList<String>

            this.checkListOfHours(totalDoctorHours, selectedDate = date)
        }
    }

    fun insertUserAppointment(doctor: Doctor, date: String, hour: String) {
        val user = FirebaseAuth.getInstance().currentUser
        val appointment = Appointment(
            id = null,
            user_id = user?.uid!!,
            doctor_id = doctor.id!!,
            date = date,
            doctor_name = doctor.name,
            hour = hour,
            iconDoctor = doctor.iconDoctorCategory

        )

        appointmentRepository.insert(appointment) { _, _ -> }
    }

    private fun checkListOfHours(listOfHours: List<String>, selectedDate: String) {
        viewModelScope.launch {
            if (selectedDate == getCurrentDate()) {
                val finalList = mutableListOf<String>()
                listOfHours.forEach {
                    val hour = it.split(":")[0]
                    if (hour.toInt() > getCurrentHour().toInt()) {
                        finalList.add(it)
                    }
                }
                doctorHours.value = finalList
            } else {
                doctorHours.value = listOfHours
            }
        }

    }
}