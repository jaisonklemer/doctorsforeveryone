package com.klemer.doctorsforeveryone.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.klemer.doctorsforeveryone.model.User
import com.klemer.doctorsforeveryone.repository.UserRepository

class ProfileViewModel : ViewModel() {

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    private val repository = UserRepository()
    private val auth = FirebaseAuth.getInstance()


    fun userUpdate(name: String, age: String, weight: String, height: String, gender: String) {
        repository.getUser(auth.currentUser?.uid!!) { user ->
            if (user != null) {

                user.name = name
                user.age = age
                user.height = height
                user.weight = weight
                user.gender = gender

                repository.updateUser(user) {

                }

            }

        }

    }

    fun getCurrentUser() {
        repository.getUser(auth.currentUser?.uid!!) { user ->
            if (user != null) {
                _user.value = user
            }
        }
    }
}