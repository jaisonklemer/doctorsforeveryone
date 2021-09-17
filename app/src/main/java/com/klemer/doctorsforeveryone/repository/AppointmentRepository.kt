package com.klemer.doctorsforeveryone.repository

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.klemer.doctorsforeveryone.model.Appointment

class AppointmentRepository {

    private val APPOINTMENT_COLLECTION = "appointments"
    private val DOCTOR_COLLECTION = "doctors"

    private val database = Firebase.firestore

    fun insert(appointment: Appointment, callback: (Boolean, String?) -> Unit) {
        val task = database.collection(APPOINTMENT_COLLECTION).add(appointment)

        task.addOnFailureListener {
            callback(false, it.localizedMessage)
        }
        task.addOnSuccessListener {
            callback(true, null)
        }
    }

    fun getAppointmentByUser(userId: String?, callback: (List<Appointment>?, String?) -> Unit) {
        val task =
            database.collection(APPOINTMENT_COLLECTION).whereEqualTo("user_id", userId).get()

        task.addOnFailureListener {
            callback(null, it.localizedMessage)
        }
        task.addOnSuccessListener { snapshots ->
            val appointments = mutableListOf<Appointment>()
            snapshots.documents.forEach { appointment ->
                appointments.add(Appointment.fromDocument(appointment))
            }

            callback(appointments, null)
        }
    }

    fun getAppointmentsByDoctor(
        doctorId: String,
        date: String,
        callback: (List<Appointment>?, String?) -> Unit
    ) {
        val task =
            database.collection(APPOINTMENT_COLLECTION).whereEqualTo("doctor_id", doctorId)
                .whereEqualTo("date", date).get()

        task.addOnFailureListener {
            callback(null, it.localizedMessage)
        }

        task.addOnSuccessListener { snapshots ->
            val listOfAppointments = mutableListOf<Appointment>()
            snapshots.documents.forEach { appointment ->
                listOfAppointments.add(Appointment.fromDocument(appointment))
            }

            callback(listOfAppointments, null)
        }

    }

}