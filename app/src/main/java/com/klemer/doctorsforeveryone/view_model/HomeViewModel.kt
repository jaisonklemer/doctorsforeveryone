package com.klemer.doctorsforeveryone.view_model

import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {


    private var query : String? = null

    fun searchDoctors(q : String){
        query= q
    }

}