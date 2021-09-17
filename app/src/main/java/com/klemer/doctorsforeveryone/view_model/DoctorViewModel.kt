package com.klemer.doctorsforeveryone.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.klemer.doctorsforeveryone.model.Appointment
import com.klemer.doctorsforeveryone.model.Doctor
import com.klemer.doctorsforeveryone.model.User
import com.klemer.doctorsforeveryone.repository.AppointmentRepository
import com.klemer.doctorsforeveryone.repository.DoctorRepository

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
        var availableHours = mutableListOf<String>()

        appointmentRepository.getAppointmentsByDoctor(
            doctorId = doctor.id!!,
            date = date
        ) { appointments, err ->
            appointments?.forEach { appointment ->
                availableHours.add(appointment.hour)
            }

            doctorHours.value =
                currentDoctorHours.filter { x -> !availableHours.contains(x) } as MutableList<String>
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
            hour = hour
        )

        appointmentRepository.insert(appointment) { success, error ->

        }
    }
}