package com.klemer.doctorsforeveryone.model

import com.google.firebase.firestore.DocumentSnapshot

data class Doctor(
    var id: String?,
    val name: String,
    val category: String,
    val biography: String,
    val working_hours: Long,
    val working_period: String
) {

    fun calculateWorkingHours(): List<String> {
        //duração da consulta
        val appointment_time = 1

        //horarios da manha
        val morning_hours = listOf("08:00-09:00", "09:00-10:00", "10:00-11:00", "11:00-12:00")

        //horarios da tarde
        val afternoon_hours =
            listOf("13:00-14:00", "14:00-15:00", "15:00-16:00", "16:00-17:00", "17:00-18:00")

        //horarios dia inteiro
        val all_day_hours =
            listOf(
                "08:00-09:00",
                "09:00-10:00",
                "10:00-11:00",
                "11:00-12:00",
                "13:00-14:00",
                "14:00-15:00",
                "15:00-16:00",
                "16:00-17:00",
                "17:00-18:00"
            )

        val listHours = mutableListOf<String>()
        val count = ((this.working_hours * appointment_time) - 1).toInt()

        when (this.working_period) {
            "Manhã" -> {
                listHours.addAll(morning_hours.slice(0..count))
            }
            "Tarde" -> {
                listHours.addAll(afternoon_hours.slice(0..count))
            }
            "Integral" -> {
                listHours.addAll(all_day_hours.slice(0..count))
            }
        }
        return listHours

    }


    companion object {
        fun fromDocument(document: DocumentSnapshot): Doctor {
            return Doctor(
                id = document.id,
                name = document["name"] as String,
                category = document["category"] as String,
                biography = document["biography"] as String,
                working_hours = document["working_hours"] as Long,
                working_period = document["working_period"] as String
            )
        }
    }

}
