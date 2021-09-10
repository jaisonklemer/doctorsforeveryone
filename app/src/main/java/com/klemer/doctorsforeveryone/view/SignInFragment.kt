package com.klemer.doctorsforeveryone.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseUser
import com.klemer.doctorsforeveryone.MainActivity
import com.klemer.doctorsforeveryone.R
import com.klemer.doctorsforeveryone.StartActivity
import com.klemer.doctorsforeveryone.databinding.SignInFragmentBinding
import com.klemer.doctorsforeveryone.utils.replaceView
import com.klemer.doctorsforeveryone.view_model.SignInViewModel

class SignInFragment : Fragment(R.layout.sign_in_fragment) {

    companion object {
        fun newInstance() = SignInFragment()
    }

    private lateinit var viewModel: SignInViewModel
    private lateinit var binding: SignInFragmentBinding

    private val loginSuccessful = Observer<FirebaseUser?> {
        //login successful
        Intent(requireContext(), MainActivity::class.java).apply {
            startActivity(this)
        }
        (requireActivity() as StartActivity).finish()
//        requireActivity().replaceView(HomeFragment.newInstance())
    }

    private val loginError = Observer<String?> {
        Toast.makeText(requireContext(), "Error: $it", Toast.LENGTH_LONG).show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        viewModel.signInGoogleOnActivityResult(requestCode, data, requireActivity())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[SignInViewModel::class.java]
        binding = SignInFragmentBinding.bind(view)

        setupObservers()
        setupClickListeners()

    }

    private fun setupObservers() {
        viewModel.loginUser.observe(viewLifecycleOwner, loginSuccessful)
        viewModel.loginError.observe(viewLifecycleOwner, loginError)
    }

    private fun setupClickListeners() {
        //button SignIn
        binding.buttonSignIn.setOnClickListener { loginUser() }

        //button create account
        binding.textViewCreateAccount.setOnClickListener {
            requireActivity().replaceView(SignUpFragment.newInstance(), R.id.containerStart)
        }

        //button SignIn With Google
        binding.signInButtonWithGoogle.setOnClickListener {
            viewModel.signIn(this, requireContext())
        }
    }

    private fun loginUser() {
        val email = binding.editTextInputEmailSignIn.text.toString()
        val pass = binding.editTextInputPasswordSignIn.text.toString()
        if (email.isNotEmpty() && pass.isNotEmpty()) {
            viewModel.signInWithEmailAndPassword(email, pass)
        }
    }

}