package com.klemer.doctorsforeveryone.repository

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.klemer.doctorsforeveryone.model.Doctor

class DoctorsRepository {

    private val DOCTOR_COLLECTION = "doctors"

    private val database = Firebase.firestore


    fun insertDoctor(doctor: Doctor, callback: (Boolean, String?) -> Unit) {
        val task = database.collection(DOCTOR_COLLECTION).add(doctor)

        task.addOnFailureListener {
            callback(false, it.localizedMessage)
        }
        task.addOnSuccessListener {
            callback(true, null)
        }
    }

    fun getDoctorById(doctorId: String, callback: (Doctor?, String?) -> Unit) {
        val task = database.collection(DOCTOR_COLLECTION).document(doctorId).get()

        task.addOnFailureListener {
            callback(null, it.localizedMessage)
        }
        task.addOnSuccessListener {
            callback(Doctor.fromDocument(it), null)
        }
    }

    fun getDoctorByCategory(doctorCategory: String, callback: (List<Doctor>?, String?) -> Unit) {
        val task =
            database.collection(DOCTOR_COLLECTION).whereEqualTo("category", doctorCategory).get()

        task.addOnFailureListener {
            callback(null, it.localizedMessage)
        }

        task.addOnSuccessListener {
            if (it.documents.isNotEmpty()) {
                val doctors = mutableListOf<Doctor>()
                it.documents.forEach { doctor ->
                    doctors.add(Doctor.fromDocument(doctor))
                }
                callback(doctors, null)
            }
        }
    }

    fun getAllDoctors(callback: (List<Doctor>?, String?) -> Unit) {
        val task = database.collection(DOCTOR_COLLECTION).get()

        task.addOnFailureListener {
            callback(null, it.localizedMessage)
        }

        task.addOnSuccessListener { listDocuments ->
            val doctors = mutableListOf<Doctor>()

            listDocuments.forEach { document ->
                doctors.add(Doctor.fromDocument(document))
            }
            callback(doctors, null)
        }
    }
}