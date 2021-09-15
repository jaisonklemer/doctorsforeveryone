package com.klemer.doctorsforeveryone.view

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.klemer.doctorsforeveryone.R
import com.klemer.doctorsforeveryone.databinding.ProfileFragmentBinding
import com.klemer.doctorsforeveryone.model.User
import com.klemer.doctorsforeveryone.view_model.ProfileViewModel

class ProfileFragment : Fragment(R.layout.profile_fragment) {

    companion object {
        fun newInstance() = ProfileFragment()

        val GENDERS = listOf("Masculino", "Feminino", "Prefiro não responder")
    }

    private lateinit var viewModel: ProfileViewModel
    private lateinit var binding: ProfileFragmentBinding
    private var selectedGender = ""

    private val observerUser = Observer<User> {
        bindUserInfo(it)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = ProfileFragmentBinding.bind(view)
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        viewModel.user.observe(viewLifecycleOwner, observerUser)
        viewModel.getCurrentUser()

        setupButtonsClick()
        setupGenderAutoComplete()

    }

    private fun setupButtonsClick() {
        binding.buttonEdit.setOnClickListener {
            setValueFieldsEnable(true)
        }
        binding.buttonSave.setOnClickListener {
            edidUser()
            setValueFieldsEnable(false)
        }
    }

    private fun edidUser() {
        val name = binding.profileName.text.toString()
        val age = binding.profileAge.text.toString()
        val weight = binding.profileWeight.text.toString()
        val height = binding.profileHeight.text.toString()
        val gender = selectedGender
        viewModel.userUpdate(name, age, weight, height, gender)
    }

    private fun bindUserInfo(user: User) {
        binding.profileName.setText(user.name)
        binding.profileAge.setText(user.age)
        binding.profileHeight.setText(user.height)
        binding.profileWeight.setText(user.weight)
        binding.autoCompleteGender.setText(user.gender, false)
        selectedGender = user.gender

    }

    private fun setValueFieldsEnable(value: Boolean) {
        binding.profileName.isEnabled = value
        binding.profileAge.isEnabled = value
        binding.profileHeight.isEnabled = value
        binding.profileWeight.isEnabled = value
        binding.buttonSave.isEnabled = value
        binding.autoCompleteLayout.isEnabled = value
        binding.buttonEdit.isEnabled = !value
    }

    private fun setupGenderAutoComplete() {
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            GENDERS
        )

        binding.autoCompleteGender.setAdapter(adapter)
        binding.autoCompleteGender.setOnItemClickListener { adapterView, view, i, l ->
            selectedGender = adapterView.getItemAtPosition(i).toString()
        }
    }

}