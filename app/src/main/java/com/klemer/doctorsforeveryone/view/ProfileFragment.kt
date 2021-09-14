package com.klemer.doctorsforeveryone.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.klemer.doctorsforeveryone.R
import com.klemer.doctorsforeveryone.databinding.ProfileFragmentBinding
import com.klemer.doctorsforeveryone.model.User
import com.klemer.doctorsforeveryone.view_model.ProfileViewModel

class ProfileFragment : Fragment(R.layout.profile_fragment) {

    companion object {
        fun newInstance() = ProfileFragment()
    }

    private lateinit var viewModel: ProfileViewModel
    private lateinit var binding: ProfileFragmentBinding

    private val observerUser = Observer<User> {
        bindUserInfo(it)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = ProfileFragmentBinding.bind(view)
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        viewModel.user.observe(viewLifecycleOwner, observerUser)
        viewModel.getCurrentUser()

        edidUser()

        binding.buttonEdit.setOnClickListener {
            setValueFieldsEnable(true)
        }
        binding.buttonSave.setOnClickListener {
            edidUser()
            setValueFieldsEnable(false)
        }

    }

    fun edidUser() {
        val name = binding.profileName.text.toString()
        val age = binding.profileAge.text.toString()
        val weight = binding.profileWeight.text.toString()
        val height = binding.profileHeight.text.toString()
        viewModel.userUpdate(name, age, weight, height)
    }

    fun bindUserInfo(user: User) {
        binding.profileName.setText(user.name)
        binding.profileAge.setText(user.age)
        binding.profileHeight.setText(user.height)
        binding.profileWeight.setText(user.weight)

    }

    fun setValueFieldsEnable(value: Boolean) {
        binding.profileName.isEnabled = value
        binding.profileAge.isEnabled = value
        binding.profileHeight.isEnabled = value
        binding.profileWeight.isEnabled = value
        binding.buttonSave.isEnabled = value
        binding.buttonEdit.isEnabled = !value
    }

}