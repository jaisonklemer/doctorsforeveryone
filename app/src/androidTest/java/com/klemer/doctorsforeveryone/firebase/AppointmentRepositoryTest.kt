package com.klemer.doctorsforeveryone.firebase

import com.klemer.doctorsforeveryone.model.Appointment
import com.klemer.doctorsforeveryone.repository.AppointmentRepository
import com.klemer.doctorsforeveryone.repository.DoctorRepository

class AppointmentRepositoryTest {

    fun test_insert_appointment() {
        val repository = AppointmentRepository()
        val doctorId = "Q8RJMLeiIIUpSkfiEphy"

        val appointment =
            Appointment(
                id = "",
                user_id = "",
                doctor_id = doctorId,
                hour = "08:00-09:00",
                date = "09/09/2021"
            )
        repository.insert(appointment) { success, error -> }
    }

    fun test_get_appointment_by_doctor() {
        val doctorId = "Q8RJMLeiIIUpSkfiEphy"

        val appointmentRepository = AppointmentRepository()
        val doctorRepository = DoctorRepository()

        doctorRepository.getDoctorById(doctorId) { doctor, s ->
            if (doctor != null) {
                val doctorHours = doctor.calculateWorkingHours()
                val horasIndisponiveis = mutableListOf<String>()

                appointmentRepository.getAppointmentsByDoctor(doctor.id!!) { list, error ->

                    list?.forEach { appointment ->
                        horasIndisponiveis.add(appointment.hour)
                    }

                    val horasDisponiveis =
                        doctorHours.filter { x -> !horasIndisponiveis.contains(x) } as MutableList<String>


                    println(horasDisponiveis)
                }
            }
        }
    }
}