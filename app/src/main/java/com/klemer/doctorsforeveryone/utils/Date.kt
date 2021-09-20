package com.klemer.doctorsforeveryone.utils

import java.util.*

fun getCurrentDay(): String {
    val date = Calendar.getInstance()
    return date.get(Calendar.DAY_OF_MONTH).toString()
}

fun getCurrentHour(): String {
    val calendar = Calendar.getInstance()
    return calendar.get(Calendar.HOUR_OF_DAY).toString()
}