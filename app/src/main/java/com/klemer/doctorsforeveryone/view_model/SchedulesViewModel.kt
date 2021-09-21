package com.klemer.doctorsforeveryone.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.klemer.doctorsforeveryone.model.Appointment
import com.klemer.doctorsforeveryone.repository.AppointmentRepository
import com.klemer.doctorsforeveryone.repository.AuthenticationRepository

class SchedulesViewModel : ViewModel() {

    private val repository = AppointmentRepository()
    private val repositoryAuth = AuthenticationRepository()

    private val _appointmentUser = MutableLiveData<List<Appointment>>()
    var appointmentUser: LiveData<List<Appointment>> = _appointmentUser

    private val _error = MutableLiveData<String>()
    var error: LiveData<String> = _error


    fun fetchAppointmentByUser(userId: String?) {
        repository.getAppointmentByUser(userId) { listAppointment, e ->
            if (listAppointment != null) {
                _appointmentUser.value = listAppointment
            }
            if (e != null) {
                _error.value = e
            }
        }
    }
    fun fetchAppointmentByStatus(status: String) {
        repository.getAppointmentByStatus(repositoryAuth.currentUser()!!.uid, status) { listAppointment, e ->
            if (listAppointment != null) {
                _appointmentUser.value = listAppointment
            }
            if (e != null) {
                _error.value = e
            }
        }
    }

    fun changeStatus(appointment: Appointment, newStatus : String){
        appointment.status = newStatus
        repository.updateAppointment(appointment)
    }
}