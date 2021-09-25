package com.klemer.doctorsforeveryone.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.VISIBLE
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseUser
import com.klemer.doctorsforeveryone.R
import com.klemer.doctorsforeveryone.SplashActivity
import com.klemer.doctorsforeveryone.StartActivity
import com.klemer.doctorsforeveryone.databinding.SignUpFragmentBinding
import com.klemer.doctorsforeveryone.utils.checkForInternet
import com.klemer.doctorsforeveryone.utils.hideKeyboard
import com.klemer.doctorsforeveryone.utils.replaceView
import com.klemer.doctorsforeveryone.view_model.SignUpViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : Fragment(R.layout.sign_up_fragment) {

    companion object {
        fun newInstance() = SignUpFragment()
    }

    private lateinit var viewModel: SignUpViewModel
    private lateinit var binding: SignUpFragmentBinding

    private val userRegistered = Observer<FirebaseUser?> {
//        requireActivity().replaceView(SignInFragment.newInstance(), R.id.containerStart)

        Intent(requireContext(), SplashActivity::class.java).apply {
            startActivity(this)
            (requireActivity() as StartActivity).finish()
        }
    }

    private val errorObserver = Observer<String?> {
        //error at create user
        if (it != null) {
            binding.progressBarSignUp.visibility = View.GONE
//            Toast.makeText(requireContext(), "Error: $it", Toast.LENGTH_LONG).show()
            Snackbar.make(requireView(), it, Snackbar.LENGTH_LONG).show()
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[SignUpViewModel::class.java]
        binding = SignUpFragmentBinding.bind(view)

        setupObservers()
        setupClickListeners()
    }

    private fun setupObservers() {
        viewModel.registeredUser.observe(viewLifecycleOwner, userRegistered)
        viewModel.createUserError.observe(viewLifecycleOwner, errorObserver)
    }

    private fun setupClickListeners() {
        binding.buttonSignUp.setOnClickListener {
            requireActivity().hideKeyboard()
            if (checkForInternet(requireContext())) {
                registerUser()
            } else {
                Snackbar.make(requireView(), "Sem conexao com a internet!", Snackbar.LENGTH_LONG)
                    .show()
            }
        }
        binding.imageViewArrowBack.setOnClickListener {
            requireActivity().replaceView(SignInFragment.newInstance(), R.id.containerStart)
        }
    }

    private fun registerUser() {
        val email = binding.editTextInputEmailSignUp.text.toString()
        val pass = binding.editTextInputPasswordSignUp.text.toString()
        if (email.isNotEmpty() && pass.isNotEmpty()) {
            binding.progressBarSignUp.visibility = VISIBLE
            viewModel.signUpWithEmailAndPassword(email, pass)
        } else {
            binding.editTextInputEmailSignUp.setError("Preencha o email")
            binding.editTextInputPasswordSignUp.setError("Preencha se senha")
        }
    }

}