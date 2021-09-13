package com.klemer.doctorsforeveryone.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.klemer.doctorsforeveryone.R
import com.klemer.doctorsforeveryone.view_model.SchedulesViewModel

class SchedulesFragment : Fragment(R.layout.schedules_fragment) {

    companion object {
        fun newInstance() = SchedulesFragment()
    }

    private lateinit var viewModel: SchedulesViewModel


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SchedulesViewModel::class.java)
        // TODO: Use the ViewModel
    }

}