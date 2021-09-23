package com.klemer.doctorsforeveryone.repository

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.klemer.doctorsforeveryone.model.Appointment
import kotlinx.coroutines.tasks.await

class AppointmentRepository {

    private val APPOINTMENT_COLLECTION = "appointments"
    private val DOCTOR_COLLECTION = "doctors"

    private val database = Firebase.firestore

    suspend fun insert(appointment: Appointment): DocumentReference {
        return database.collection(APPOINTMENT_COLLECTION).add(appointment).await()
    }

    suspend fun getAppointmentByUser(
        userId: String?,
    ): QuerySnapshot {
        return database.collection(APPOINTMENT_COLLECTION).whereEqualTo("user_id", userId).get()
            .await()
    }

    suspend fun getAppointmentByStatus(
        userId: String?,
        appointmentStatus: String?
    ): QuerySnapshot {
        return database.collection(APPOINTMENT_COLLECTION).whereEqualTo("user_id", userId)
            .whereEqualTo("status", appointmentStatus)
            .orderBy("date", Query.Direction.ASCENDING)
            .orderBy("hour", Query.Direction.ASCENDING)
            .get().await()
    }

    suspend fun getAppointmentByStatusAndDate(
        userId: String,
        appointmentStatus: String,
        date: String,
        hour: String
    ): QuerySnapshot {
        return database.collection(APPOINTMENT_COLLECTION).whereEqualTo("user_id", userId)
            .whereEqualTo("hour", hour)
            .whereEqualTo("status", appointmentStatus).whereEqualTo("date", date).get().await()
    }

    suspend fun getAppointmentsByDoctor(doctorId: String, date: String): QuerySnapshot {
        return database.collection(APPOINTMENT_COLLECTION).whereEqualTo("doctor_id", doctorId)
            .whereEqualTo("date", date).get().await()

    }

    suspend fun updateAppointment(appointment: Appointment) {
        database.collection(APPOINTMENT_COLLECTION).document(appointment.id!!)
            .set(appointment).await()
    }

}