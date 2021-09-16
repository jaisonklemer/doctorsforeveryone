package com.klemer.doctorsforeveryone.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.klemer.doctorsforeveryone.model.Doctor
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
}