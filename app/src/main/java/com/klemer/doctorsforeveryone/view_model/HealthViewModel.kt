package com.klemer.doctorsforeveryone.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.klemer.doctorsforeveryone.model.HealthNewsResponse
import com.klemer.doctorsforeveryone.repository.NewsRepository

class HealthViewModel : ViewModel() {

    private val repository = NewsRepository()

    val news = MutableLiveData<HealthNewsResponse>()

    fun getNews() {
        repository.getNews { response ->
            news.value = response
        }
    }
}