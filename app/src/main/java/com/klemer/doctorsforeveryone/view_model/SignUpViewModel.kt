package com.klemer.doctorsforeveryone.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import com.klemer.doctorsforeveryone.repository.AuthenticationRepository

class SignUpViewModel : ViewModel() {

    private val repository = AuthenticationRepository()

    var registeredUser = MutableLiveData<FirebaseUser?>()
    var createUserError = MutableLiveData<String?>()

    fun signUpWithEmailAndPassword(email: String, password: String) {
        repository.signUpWithEmailAndPassword(email = email, password = password) { user, error ->
            if (user != null) {
                registeredUser.value = user
            } else {
                createUserError.value = error
            }
        }
    }
}