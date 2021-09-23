package com.klemer.doctorsforeveryone.repository

import com.klemer.doctorsforeveryone.model.HealthNewsResponse
import com.klemer.doctorsforeveryone.services.HealthServiceAPI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import retrofit2.Response
import javax.inject.Inject

class NewsRepository @Inject constructor(private val service: HealthServiceAPI) {

    suspend fun getNews(): HealthNewsResponse? {
        return CoroutineScope(Dispatchers.Default).async {
            val response = service.getHealthNews()
            processData(response)
        }.await()
    }

    private fun <T> processData(response: Response<T>): T? {
        return if (response.isSuccessful) response.body()
        else null
    }
}

