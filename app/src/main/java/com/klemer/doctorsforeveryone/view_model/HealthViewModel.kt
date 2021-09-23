package com.klemer.doctorsforeveryone.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.klemer.doctorsforeveryone.model.HealthNews
import com.klemer.doctorsforeveryone.model.HealthNewsResponse
import com.klemer.doctorsforeveryone.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HealthViewModel @Inject constructor(private val repository: NewsRepository) : ViewModel() {

    val _newsResponse = MutableLiveData<HealthNewsResponse>()
    val newsResponse : LiveData<HealthNewsResponse> = _newsResponse

    fun getNews(){
        viewModelScope.launch {
            val responseNews = repository.getNews()
            responseNews?.let{
                _newsResponse.value = it
            }
        }
    }
}