package com.klemer.doctorsforeveryone.model

import com.google.common.truth.Truth.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test

class DoctorTest {
    private lateinit var doctor: Doctor

    @Before
    fun setUp() {
        doctor = Doctor(
            "1232",
            "João",
            "Dentista",
            "Lorem ipsum..",
            4L,
            "Manhã",
            null,
            null
        )
    }

    @After
    fun tearDown() {
    }

    @Test
    fun calculateWorkingHours() {
        val hours = doctor.calculateWorkingHours()
        val expected = listOf("08:00-09:00", "09:00-10:00", "10:00-11:00", "11:00-12:00")

        assertThat(hours).isEqualTo(expected)
    }
}