package com.klemer.doctorsforeveryone.repository

import com.klemer.doctorsforeveryone.model.HealthNewsResponse
import com.klemer.doctorsforeveryone.services.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsRepository {

    private val api = RetrofitService().getHealthService()

    fun getNews(callback: (HealthNewsResponse) -> Unit) {
        api.getHealthNews().enqueue(object : Callback<HealthNewsResponse> {
            override fun onResponse(
                call: Call<HealthNewsResponse>,
                response: Response<HealthNewsResponse>
            ) {
                response.body()?.let { callback(it) }
            }

            override fun onFailure(call: Call<HealthNewsResponse>, t: Throwable) {
                println(t.localizedMessage)
            }

        })
    }
}