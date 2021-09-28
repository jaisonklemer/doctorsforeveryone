package com.klemer.doctorsforeveryone.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.klemer.doctorsforeveryone.MainActivity
import com.klemer.doctorsforeveryone.R
import com.klemer.doctorsforeveryone.view_model.AdminViewModel

class AdminFragment : Fragment(R.layout.admin_fragment) {

    companion object {
        fun newInstance() = AdminFragment()
    }

    private lateinit var viewModel: AdminViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        changeStateBackPressed()

        viewModel = ViewModelProvider(this).get(AdminViewModel::class.java)
    }

    private fun changeStateBackPressed() {
        (activity as MainActivity)
            .findViewById<BottomNavigationView>(R.id.bottomNavigation)
            .menu
            .getItem(4)
            .isChecked = true
    }

}