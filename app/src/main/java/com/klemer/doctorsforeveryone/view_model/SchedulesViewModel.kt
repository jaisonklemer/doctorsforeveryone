package com.klemer.doctorsforeveryone.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.klemer.doctorsforeveryone.model.Appointment
import com.klemer.doctorsforeveryone.repository.AppointmentRepository
import com.klemer.doctorsforeveryone.repository.AuthenticationRepository
import kotlinx.coroutines.launch

class SchedulesViewModel : ViewModel() {

    private val repository = AppointmentRepository()
    private val repositoryAuth = AuthenticationRepository()

    private val _appointmentUser = MutableLiveData<List<Appointment>>()
    var appointmentUser: LiveData<List<Appointment>> = _appointmentUser

    private val _error = MutableLiveData<String>()
    var error: LiveData<String> = _error


    fun fetchAppointmentByUser(userId: String?) {
        viewModelScope.launch {
            try {
                val appointments = mutableListOf<Appointment>()
                val result = repository.getAppointmentByUser(userId)

                result.documents.forEach { appointment ->
                    appointments.add(Appointment.fromDocument(appointment))
                }
                _appointmentUser.value = appointments

            } catch (e: Exception) {
                _error.value = e.localizedMessage
            }
        }
    }

    fun fetchAppointmentByStatus(status: String) {
        viewModelScope.launch {
            try {
                val result = repository.getAppointmentByStatus(
                    repositoryAuth.currentUser()!!.uid,
                    status
                )
                val listAppointment = mutableListOf<Appointment>()

                result.documents.forEach { appointment ->
                    listAppointment.add(Appointment.fromDocument(appointment))
                }
                _appointmentUser.value = listAppointment

            } catch (e: Exception) {
                _error.value = e.localizedMessage
            }
        }
    }

    fun changeStatus(appointment: Appointment, newStatus: String) {

        viewModelScope.launch {
            try {
                appointment.status = newStatus
                repository.updateAppointment(appointment)
            } catch (e: Exception) {
                _error.value = e.localizedMessage
            }
        }
    }
}