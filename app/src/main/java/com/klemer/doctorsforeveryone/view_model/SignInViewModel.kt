package com.klemer.doctorsforeveryone.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import com.klemer.doctorsforeveryone.repository.AuthenticationRepository

class SignInViewModel : ViewModel() {

    private val repository = AuthenticationRepository()

    var loginUser = MutableLiveData<FirebaseUser?>()
    var loginError = MutableLiveData<String?>()


    fun signInWithEmailAndPassword(email: String, password: String) {
        repository.signInWithEmailAndPassword(email = email, password = password) { user, error ->
            if (user != null) {
                loginUser.value = user
            } else {
                loginError.value = error
            }
        }
    }
}