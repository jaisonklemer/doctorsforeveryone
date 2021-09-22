package com.klemer.doctorsforeveryone.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.klemer.doctorsforeveryone.repository.AuthenticationRepository
import kotlinx.coroutines.launch

class SignUpViewModel : ViewModel() {

    private val repository = AuthenticationRepository()

    var registeredUser = MutableLiveData<FirebaseUser?>()
    var createUserError = MutableLiveData<String?>()

    fun signUpWithEmailAndPassword(email: String, password: String) {
        viewModelScope.launch {
            try {
                val result =
                    repository.signUpWithEmailAndPassword(email = email, password = password)
                registeredUser.value = result.user
            } catch (e: Exception) {
                createUserError.value = e.localizedMessage
            }
        }
    }
}