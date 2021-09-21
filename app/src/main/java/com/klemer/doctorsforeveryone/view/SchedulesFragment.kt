package com.klemer.doctorsforeveryone.view

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.klemer.doctorsforeveryone.MainActivity
import com.klemer.doctorsforeveryone.R
import com.klemer.doctorsforeveryone.adapter.AppointmentAdapter
import com.klemer.doctorsforeveryone.databinding.SchedulesFragmentBinding
import com.klemer.doctorsforeveryone.model.Appointment
import com.klemer.doctorsforeveryone.repository.AuthenticationRepository
import com.klemer.doctorsforeveryone.utils.configSnackbar
import com.klemer.doctorsforeveryone.view_model.SchedulesViewModel

class SchedulesFragment : Fragment(R.layout.schedules_fragment) {

    companion object {
        fun newInstance() = SchedulesFragment()
    }

    private lateinit var viewModel: SchedulesViewModel
    private lateinit var binding: SchedulesFragmentBinding

    private var adapterAppointment = AppointmentAdapter {
        alertCancelAppointment(it)
    }

    private val observerAppoinment = Observer<List<Appointment>> {
        if (it.isEmpty()) {
            val view =
                (requireActivity() as MainActivity).findViewById<BottomNavigationView>(R.id.bottomNavigation)
            configSnackbar(view,"Nenhum agendamento encontrado!")
        }
        adapterAppointment.refresh(it)
    }

    private val observerError = Observer<String> {
        val view =
            (requireActivity() as MainActivity).findViewById<BottomNavigationView>(R.id.bottomNavigation)
        configSnackbar(view,"Nenhum agendamento!")

    }

    override fun onStart() {
        super.onStart()
        viewModel.fetchAppointmentByStatus("Agendado")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadComponents(view)
        setupObserver()
        setupRecyclerView()
        getStatus()

    }

    private fun loadComponents(view: View) {
        binding = SchedulesFragmentBinding.bind(view)
        viewModel = ViewModelProvider(this).get(SchedulesViewModel::class.java)
    }

    private fun setupObserver() {
        viewModel.appointmentUser.observe(viewLifecycleOwner, observerAppoinment)
        viewModel.error.observe(viewLifecycleOwner, observerError)
    }

    private fun setupRecyclerView() = with(binding.recyclerViewAppointment) {
        layoutManager = LinearLayoutManager(requireContext())
        adapter = adapterAppointment

    }

    private fun alertCancelAppointment(appointment: Appointment) {
        AlertDialog.Builder(context)
            .setTitle("Cancelar")
            .setMessage("Deseja cancelar agendamento?")
            .setPositiveButton(R.string.yes) { dialog, which ->
                viewModel.changeStatus(appointment, "Cancelado")
                viewModel.fetchAppointmentByUser(AuthenticationRepository().currentUser()?.uid)
            }
            .setNegativeButton(R.string.no) { dialog, which ->
            }
            .create()
            .show()
    }

    private fun getStatus() {
        binding.chipGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.chipAgendada -> viewModel.fetchAppointmentByStatus("Agendado")
                R.id.chipCancelada -> viewModel.fetchAppointmentByStatus("Cancelado")
                R.id.chipConcluida -> viewModel.fetchAppointmentByStatus("Concluído")
            }
        }
    }
}