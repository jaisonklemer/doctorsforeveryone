package com.klemer.doctorsforeveryone.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.klemer.doctorsforeveryone.model.User
import com.klemer.doctorsforeveryone.repository.AuthenticationRepository
import com.klemer.doctorsforeveryone.repository.UserRepository

class HomeViewModel : ViewModel() {

    private val auth = AuthenticationRepository()
    private val userRepository = UserRepository()

    val currentUser = MutableLiveData<User>()

    fun getCurrentUser() {
        auth.currentUser().let { user ->
            if (user != null) {
                userRepository.getUser(user.uid) {
                    currentUser.value = it
                }
            }
        }


    }

}