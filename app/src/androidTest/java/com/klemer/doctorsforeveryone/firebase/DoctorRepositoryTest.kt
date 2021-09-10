package com.klemer.doctorsforeveryone.firebase

import com.klemer.doctorsforeveryone.model.Doctor
import com.klemer.doctorsforeveryone.repository.DoctorsRepository

class DoctorRepositoryTest {

    fun test_insert_doctor() {
        val repository = DoctorsRepository()

        val doctor = Doctor(
            id = null,
            name = "Botão",
            category = "Dentista",
            biography = "Lorem Ipsum",
            working_hours = 4,
            working_period = "Tarde"
        )
        repository.insertDoctor(doctor) { success, error ->

        }
    }

    fun test_get_doctor_by_id() {
        val doctor_id = "Q8RJMLeiIIUpSkfiEphy"

        val repository = DoctorsRepository()

        repository.getDoctorById(doctor_id) { doctor, s ->

        }
    }

    fun test_get_doctor_by_category() {
        val category = "Dentista"

        val repository = DoctorsRepository()

        repository.getDoctorByCategory(category) { doctors, error ->

        }
    }

    fun test_get_all_doctor() {

        val repository = DoctorsRepository()

        repository.getAllDoctors { doctors, error ->
            doctors?.forEach { doctor ->
                println(doctor.calculateWorkingHours())
            }
        }
    }
}