package com.klemer.doctorsforeveryone.repository

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.klemer.doctorsforeveryone.model.Doctor
import kotlinx.coroutines.tasks.await

class DoctorRepository {

    private val DOCTOR_COLLECTION = "doctors"

    private val database = Firebase.firestore


    suspend fun insertDoctor(doctor: Doctor): DocumentReference {
        return database.collection(DOCTOR_COLLECTION).add(doctor).await()
    }

    suspend fun getDoctorById(doctorId: String): DocumentSnapshot {
        return database.collection(DOCTOR_COLLECTION).document(doctorId).get().await()
    }

    suspend fun getDoctorByCategory(doctorCategory: String): QuerySnapshot {
        return database.collection(DOCTOR_COLLECTION).whereEqualTo("category", doctorCategory)
            .orderBy("name").get().await()
    }

    suspend fun getAllDoctors(): QuerySnapshot {
        return database.collection(DOCTOR_COLLECTION).orderBy("name").get().await()
    }

    suspend fun getDoctorsWithCateogry(doctorName: String, doctorCategory: String): QuerySnapshot {
        return database.collection(DOCTOR_COLLECTION)
            .whereEqualTo("category", doctorCategory)
            .orderBy("name")
            .startAt(doctorName)
            .endAt(doctorName + '\uf8ff')
            .get().await()
    }


    suspend fun getDoctorByName(doctorName: String): QuerySnapshot {
        return database.collection(DOCTOR_COLLECTION)
            .orderBy("name")
            .startAt(doctorName)
            .endAt(doctorName + '\uf8ff').get().await()

    }

}