package com.klemer.doctorsforeveryone.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
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
    lateinit var doctor: Doctor

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_sheet_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = BottomSheetFragmentBinding.bind(view)
        viewModel = ViewModelProvider(this).get(BottomSheetViewModel::class.java)
        getArgumentsDoctor()
        loadDoctor()

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
        Glide.with(requireContext()).load(doctor.iconDoctorCategory).into(binding.iconSpecialtyDoctor)
    }
}