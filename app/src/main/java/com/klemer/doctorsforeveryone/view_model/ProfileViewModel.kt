package com.klemer.doctorsforeveryone.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.klemer.doctorsforeveryone.model.User
import com.klemer.doctorsforeveryone.repository.AuthenticationRepository
import com.klemer.doctorsforeveryone.repository.UserRepository
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {

    private val _user = MutableLiveData<User>()
    private val _error = MutableLiveData<String>()

    val user: LiveData<User> = _user
    val error: LiveData<String> = _error

    private val repository = UserRepository()
    private val logOut = AuthenticationRepository()
    private val auth = FirebaseAuth.getInstance()


    fun userUpdate(name: String, age: String, weight: String, height: String, gender: String) {
        viewModelScope.launch {
            try {
                val userInfo = repository.getUser(auth.currentUser?.uid!!)
                User.fromDocument(userInfo).let { user ->
                    user.name = name
                    user.age = age
                    user.height = height
                    user.weight = weight
                    user.gender = gender

                    repository.updateUser(user)
                }
            } catch (e: Exception) {
                _error.value = e.localizedMessage
            }
        }

//        repository.getUser(auth.currentUser?.uid!!) { user ->
//            if (user != null) {
//
//
//                repository.updateUser(user) {
//                }
//            }
//        }
    }

    fun getCurrentUser() {
        viewModelScope.launch {
            try {
                val userInfo = repository.getUser(auth.currentUser?.uid!!)
                _user.value = User.fromDocument(userInfo)

            } catch (e: Exception) {
                _error.value = e.localizedMessage
            }
        }
//        repository.getUser(auth.currentUser?.uid!!) { user ->
//            if (user != null) {
//                _user.value = user
//            }
//        }
    }

    fun signOut() {
        logOut.signOut()
    }
}