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
import com.klemer.doctorsforeveryone.utils.getCurrentHour
import kotlinx.coroutines.launch

class DoctorViewModel : ViewModel() {

    private val repository = DoctorRepository()
    private val appointmentRepository = AppointmentRepository()

    var doctorGet = MutableLiveData<List<Doctor>?>()
    var doctor = MutableLiveData<Doctor>()
    var doctorInsert = MutableLiveData<Boolean>()

    var error = MutableLiveData<String>()
    var doctorHours = MutableLiveData<List<String>>()
    var appointmentInserted = MutableLiveData<Boolean>()

    fun fetchDoctor() {
        viewModelScope.launch {
            try {
                val result = repository.getAllDoctors()

                val doctors = mutableListOf<Doctor>()
                result.documents.forEach { document ->
                    doctors.add(Doctor.fromDocument(document))
                }

                doctorGet.value = doctors

            } catch (e: Exception) {
                error.value = e.localizedMessage
            }
        }
    }

    fun fetchDoctorByCategory(name: String) {
        viewModelScope.launch {
            try {
                val result = repository.getDoctorByCategory(name)
                val doctors = mutableListOf<Doctor>()

                result.documents.forEach { doctor ->
                    doctors.add(Doctor.fromDocument(doctor))
                }
                doctorGet.value = doctors

            } catch (e: Exception) {
                error.value = e.localizedMessage
            }
        }
    }

    fun fetchDoctorByName(nameDoc: String) {
        viewModelScope.launch {
            try {
                val result = repository.getDoctorByName(nameDoc)
                val doctors = mutableListOf<Doctor>()

                result.documents.forEach { doctor ->
                    doctors.add(Doctor.fromDocument(doctor))
                }
                doctorGet.value = doctors

            } catch (e: Exception) {
                error.value = e.localizedMessage
            }
        }
    }

    fun insertDoctor(doctor: Doctor) {
        viewModelScope.launch {
            try {
                repository.insertDoctor(doctor)
                doctorInsert.value = true
            } catch (e: Exception) {
                error.value = e.localizedMessage
            }
        }

    }

    fun getDoctorById(doctorId: String) {
        viewModelScope.launch {
            try {
                val result = repository.getDoctorById(doctorId = doctorId)
                doctor.value = Doctor.fromDocument(result)
            } catch (e: Exception) {
                error.value = e.localizedMessage
            }
        }
    }

    fun getDoctorHours(doctor: Doctor, date: String) {
        val currentDoctorHours = doctor.calculateWorkingHours()
        val availableHours = mutableListOf<String>()

        viewModelScope.launch {
            try {
                val result = appointmentRepository.getAppointmentsByDoctor(
                    doctorId = doctor.id!!,
                    date = date
                )
                val listOfAppointments = mutableListOf<Appointment>()
                result.documents.forEach { appointment ->
                    listOfAppointments.add(Appointment.fromDocument(appointment))
                }

                listOfAppointments.forEach { appointment ->
                    availableHours.add(appointment.hour)
                }

                val totalDoctorHours =
                    currentDoctorHours.filter { x -> !availableHours.contains(x) }
                            as MutableList<String>

                checkListOfHours(totalDoctorHours, selectedDate = date)

            } catch (e: Exception) {
                error.value = e.localizedMessage
            }
        }
    }

    fun insertUserAppointment(doctor: Doctor, date: String, hour: String) {

        viewModelScope.launch {
            try {
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

                appointmentRepository.insert(appointment)
                appointmentInserted.value = true

            } catch (e: Exception) {
                error.value = e.localizedMessage
            }
        }


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