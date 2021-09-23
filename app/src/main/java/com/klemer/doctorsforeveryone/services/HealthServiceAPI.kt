package com.klemer.doctorsforeveryone.services

import com.klemer.doctorsforeveryone.model.HealthNewsResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface HealthServiceAPI {

    @GET("v2/top-headlines")
suspend fun getHealthNews(
        @Query("country") country: String = "br",
        @Query("category") category: String = "health",
        @Query("apiKey") apiKey: String = "edc1dc88d54644f58386ed0abc8edf6f"
    ): Response<HealthNewsResponse>
}