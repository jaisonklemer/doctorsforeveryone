package com.klemer.doctorsforeveryone.model

import com.google.firebase.firestore.DocumentSnapshot

data class Appointment(
    var id: String?,
    var user_id: String, //auth.currentUser.id
    var doctor_id: String, // doctorSelected.id
    var hour: String, // 08:00-09:00
    var date: String, //dia da consulta
    var doctor_name: String,
    var status: String = "Agendado"

) {

    companion object {
        fun fromDocument(document: DocumentSnapshot): Appointment {
            return Appointment(
                id = document.id,
                user_id = document["user_id"] as String,
                doctor_id = document["doctor_id"] as String,
                hour = document["hour"] as String,
                date = document["date"] as String,
                doctor_name = document["doctor_name"] as String,
                status = document["status"] as String
            )
        }
    }
}
