package com.klemer.doctorsforeveryone.view_model

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseUser
import com.klemer.doctorsforeveryone.repository.AuthenticationRepository
import com.klemer.doctorsforeveryone.utils.getFirebaseError
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(@ApplicationContext val context: Context) : ViewModel() {

    private val repository = AuthenticationRepository()

    var registeredUser = MutableLiveData<FirebaseUser?>()
    var createUserError = MutableLiveData<String?>()

    fun signUpWithEmailAndPassword(email: String, password: String) {
        viewModelScope.launch {
            try {
                val result =
                    repository.signUpWithEmailAndPassword(email = email, password = password)
                registeredUser.value = result.user
            } catch (e: FirebaseAuthException) {
                createUserError.value = context.getFirebaseError(e.errorCode)
            }
        }
    }
}