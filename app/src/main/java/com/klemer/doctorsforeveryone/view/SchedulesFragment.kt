package com.klemer.doctorsforeveryone.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.klemer.doctorsforeveryone.R
import com.klemer.doctorsforeveryone.adapter.AppointmentAdapter
import com.klemer.doctorsforeveryone.databinding.SchedulesFragmentBinding
import com.klemer.doctorsforeveryone.model.Appointment
import com.klemer.doctorsforeveryone.repository.AuthenticationRepository
import com.klemer.doctorsforeveryone.view_model.SchedulesViewModel

class SchedulesFragment : Fragment(R.layout.schedules_fragment) {

    companion object {
        fun newInstance() = SchedulesFragment()
    }

    private lateinit var viewModel: SchedulesViewModel
    private lateinit var binding: SchedulesFragmentBinding
    private var adapterAppointment = AppointmentAdapter()

    private val observerAppoinment = Observer<List<Appointment>> {
        println(it)
        adapterAppointment.refresh(it)
    }

    private val observerError = Observer<String> {
        Snackbar.make(requireView(), "Nenhum appointment cadastrado!", Snackbar.LENGTH_LONG)
            .show()
    }

    override fun onStart() {
        super.onStart()
        viewModel.fetchAppointmentByUser(AuthenticationRepository().currentUser()?.uid)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadComponents(view)
        setupObserver()
        setupRecyclerView()
    }

    private fun loadComponents(view: View) {
        binding = SchedulesFragmentBinding.bind(view)
        viewModel = ViewModelProvider(this).get(SchedulesViewModel::class.java)
    }

    private fun setupObserver() {
        viewModel.appointmentUser.observe(viewLifecycleOwner, observerAppoinment)
        viewModel.error.observe(viewLifecycleOwner, observerError)
    }

    private fun setupRecyclerView() = with(binding.recyclerViewAppointment){
        layoutManager = LinearLayoutManager(requireContext())
        adapter = adapterAppointment

    }

    private fun executeComponents() {

    }


}