package com.klemer.doctorsforeveryone.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.klemer.doctorsforeveryone.model.User
import com.klemer.doctorsforeveryone.repository.AuthenticationRepository
import com.klemer.doctorsforeveryone.repository.UserRepository
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val auth = AuthenticationRepository()
    private val userRepository = UserRepository()

    val currentUser = MutableLiveData<User>()
    val error = MutableLiveData<String>()

    fun getCurrentUser() {
        viewModelScope.launch {
            try {
                val firebaseUser = auth.currentUser()
                firebaseUser?.let {
                    currentUser.value = User.fromDocument(userRepository.getUser(firebaseUser.uid))
                }
            } catch (e: Exception) {
                error.value = e.localizedMessage
            }
        }
    }

}