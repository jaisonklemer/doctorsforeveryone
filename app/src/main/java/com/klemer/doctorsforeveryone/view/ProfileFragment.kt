package com.klemer.doctorsforeveryone.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.klemer.doctorsforeveryone.R
import com.klemer.doctorsforeveryone.databinding.ProfileFragmentBinding
import com.klemer.doctorsforeveryone.view_model.ProfileViewModel

class ProfileFragment : Fragment(R.layout.profile_fragment) {

    companion object {
        fun newInstance() = ProfileFragment()
    }

    private lateinit var viewModel: ProfileViewModel
    private lateinit var binding: ProfileFragmentBinding



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = ProfileFragmentBinding.bind(view)


        binding.buttonSave.setOnClickListener{
            val name = binding.profileName.text
            val age = binding.profileAge.text
            val weight = binding.profileWeight.text
            val heigh = binding.profileHeight.text

        }
    }

}