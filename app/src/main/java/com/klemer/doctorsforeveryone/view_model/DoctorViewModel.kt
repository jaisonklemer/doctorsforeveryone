package com.klemer.doctorsforeveryone.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.klemer.doctorsforeveryone.model.Doctor
import com.klemer.doctorsforeveryone.repository.DoctorRepository

class DoctorViewModel : ViewModel() {

    private val repository = DoctorRepository()

    var doctorGetAll = MutableLiveData<List<Doctor>>()
    var doctorInsert = MutableLiveData<Boolean>()
    var error = MutableLiveData<String>()

    fun fetchDoctor() {
        repository.getAllDoctors { listDoc, e ->
            doctorGetAll.value = listDoc
            error.value = e
        }
    }

    fun insertDoctor(doctor: Doctor) {
        repository.insertDoctor(doctor) { insertDoctor, e ->
            doctorInsert.value = insertDoctor
            error.value = e
        }
    }
}