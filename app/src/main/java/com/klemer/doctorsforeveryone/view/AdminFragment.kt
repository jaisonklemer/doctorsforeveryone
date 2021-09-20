package com.klemer.doctorsforeveryone.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.klemer.doctorsforeveryone.R
import com.klemer.doctorsforeveryone.view_model.AdminViewModel

class AdminFragment : Fragment(R.layout.admin_fragment) {

    companion object {
        fun newInstance() = AdminFragment()
    }

    private lateinit var viewModel: AdminViewModel


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AdminViewModel::class.java)
        // TODO: Use the ViewModel
    }

}