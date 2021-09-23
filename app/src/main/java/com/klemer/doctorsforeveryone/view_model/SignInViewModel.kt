package com.klemer.doctorsforeveryone.view_model

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.klemer.doctorsforeveryone.model.User
import com.klemer.doctorsforeveryone.repository.AuthenticationRepository
import com.klemer.doctorsforeveryone.repository.UserRepository
import kotlinx.coroutines.launch

class SignInViewModel : ViewModel() {

    private val repository = AuthenticationRepository()
    private val userRepository = UserRepository()

    var loginUser = MutableLiveData<FirebaseUser?>()
    var currentUserInfo = MutableLiveData<User>()
    var loginError = MutableLiveData<String?>()


    fun signInWithEmailAndPassword(email: String, password: String) {
        viewModelScope.launch {
            try {
                val authResult = repository.signInWithEmailAndPassword(email, password)
                loginUser.value = authResult.user

            } catch (e: Exception) {
                loginError.value = e.localizedMessage
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

    fun currentUser(): FirebaseUser? {
        return repository.currentUser()
    }

    fun signOut() {
        repository.signOut()
    }

    fun getCurrentUserInfo(userId: String) {
        viewModelScope.launch {
            try {
                val result = userRepository.getUser(userId)
                if (result.exists())
                    currentUserInfo.value = User.fromDocument(result)
            } catch (e: Exception) {
                loginError.value = e.localizedMessage
            }
        }
    }

}