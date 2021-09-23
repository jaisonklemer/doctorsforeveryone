package com.klemer.doctorsforeveryone.firebase

import com.klemer.doctorsforeveryone.model.Doctor
import com.klemer.doctorsforeveryone.repository.DoctorRepository

class DoctorRepositoryTest {

    fun test_insert_doctor() {
        val repository = DoctorRepository()

        val doctor = Doctor(
            id = null,
            name = "Botão",
            category = "Dentista",
            biography = "Lorem Ipsum",
            working_hours = 4,
            working_period = "Tarde",
            avatarDoctor = "https://firebasestorage.googleapis.com/v0/b/doctors-for-everyone.appspot.com/o/Dr_1.png?alt=media&token=79dc3235-6c82-4173-84a1-89bcf352cea4",
            iconDoctorCategory = "https://firebasestorage.googleapis.com/v0/b/doctors-for-everyone.appspot.com/o/icon_green_oftalmologista.png?alt=media&token=484ad008-bfb7-42cc-b47c-60303c8d30b4"
        )
        repository.insertDoctor(doctor) { success, error ->

        }
    }

    fun test_get_doctor_by_id() {
        val doctor_id = "Q8RJMLeiIIUpSkfiEphy"

        val repository = DoctorRepository()

        repository.getDoctorById(doctor_id) { doctor, s ->

        }
    }

    fun test_get_doctor_by_category() {
        val category = "Dentista"

        val repository = DoctorRepository()

        repository.getDoctorByCategory(category) { doctors, error ->

        }
    }

    fun test_get_all_doctor() {

        val repository = DoctorRepository()

        repository.getAllDoctors { doctors, error ->
            doctors?.forEach { doctor ->
                println(doctor.calculateWorkingHours())
            }
        }
    }
}