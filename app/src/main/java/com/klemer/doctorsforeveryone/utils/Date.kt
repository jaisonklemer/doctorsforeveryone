package com.klemer.doctorsforeveryone.utils

import java.text.SimpleDateFormat
import java.util.*

fun getCurrentDay(): String {
    val date = Calendar.getInstance()
    return date.get(Calendar.DAY_OF_MONTH).toString()
}

fun getCurrentHour(): String {
    val calendar = Calendar.getInstance()
    return calendar.get(Calendar.HOUR_OF_DAY).toString()
}

fun getCurrentDate(): String {
    return formatDate(Date())
}

fun formatDate(date: Date, pattern: String = "dd/MM/yyyy"): String {
    val sdf = SimpleDateFormat(pattern)
    return sdf.format(date)
}