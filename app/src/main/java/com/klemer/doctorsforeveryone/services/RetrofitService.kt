package com.klemer.doctorsforeveryone.services

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitService {

    private fun getInstance(path: String): Retrofit {
        return Retrofit
            .Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(path)
            .build()
    }

    fun getHealthService(): HealthServiceAPI {
        return getInstance("https://newsapi.org/").create(HealthServiceAPI::class.java)
    }
}