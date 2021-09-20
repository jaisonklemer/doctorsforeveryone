package com.klemer.doctorsforeveryone.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.klemer.doctorsforeveryone.DoctorActivity
import com.klemer.doctorsforeveryone.MainActivity
import com.klemer.doctorsforeveryone.R
import com.klemer.doctorsforeveryone.databinding.BottomSheetFragmentBinding
import com.klemer.doctorsforeveryone.model.Doctor
import com.klemer.doctorsforeveryone.view_model.BottomSheetViewModel

class BottomSheetFragment : BottomSheetDialogFragment() {

    companion object {
        fun newInstance() = BottomSheetFragment()
    }

    private lateinit var viewModel: BottomSheetViewModel
    private lateinit var binding: BottomSheetFragmentBinding
    private lateinit var doctorActivityResult: ActivityResultLauncher<Intent>
    lateinit var doctor: Doctor

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_sheet_fragment, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerDoctorActivityResult()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = BottomSheetFragmentBinding.bind(view)
        viewModel = ViewModelProvider(this).get(BottomSheetViewModel::class.java)
        getArgumentsDoctor()
        loadDoctor()
        binding.buttonAppointment.setOnClickListener { startDoctorActivity(doctor.id) }

    }

    override fun getTheme(): Int = R.style.CustomBottomSheetDialog

    private fun getArgumentsDoctor() {
        doctor = arguments?.getSerializable("doctor") as Doctor

    }

    private fun loadDoctor() {

        binding.nameDoctor.text = doctor.name
        binding.nameDoctorCategory.text = doctor.category
        binding.descriptionDoctor.text = doctor.biography
        Glide.with(requireContext()).load(doctor.avatarDoctor).into(binding.avatarDoctor)
        Glide.with(requireContext()).load(doctor.iconDoctorCategory)
            .into(binding.iconSpecialtyDoctor)
    }

    private fun startDoctorActivity(doctorId: String?) {
        if (doctorId != null) {
            Intent(requireContext(), DoctorActivity::class.java).apply {
                putExtra("doctor_id", doctorId)
                doctorActivityResult.launch(this)
            }

        }
    }

    @SuppressLint("ResourceType")
    private fun registerDoctorActivityResult() {
        doctorActivityResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.data?.hasExtra("created_appointment") == true) {
                    this.dismiss()
                    (requireActivity() as MainActivity?)?.changeBottomSelectedItem(
                        SchedulesFragment.newInstance(),
                        1
                    )
                }
            }
    }
}