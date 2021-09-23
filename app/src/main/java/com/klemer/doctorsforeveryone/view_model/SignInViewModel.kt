package com.klemer.doctorsforeveryone.view_model

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
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

    fun signInGoogleOnActivityResult(requestCode: Int, data: Intent?, activity: Activity) {
        repository.signInGoogleOnActivityResult(requestCode, data, activity) { user, error ->
            loginUser.value = user
            loginError.value = error
        }
    }

    fun signIn(fragment: Fragment, context: Context) {
        repository.signIn(fragment, context)
    }

    fun currentUser() : FirebaseUser?{
        return repository.currentUser()
    }

    fun signOut() {
        repository.signOut()
    }

}