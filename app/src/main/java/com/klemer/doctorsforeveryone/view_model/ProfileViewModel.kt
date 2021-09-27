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
    private val authentication = AuthenticationRepository()
    private val auth = FirebaseAuth.getInstance()


    fun userUpdate(name: String, age: String, weight: String, height: String, gender: String) {
        viewModelScope.launch {
            try {
                val isComplete =
                    (name != "" && age != "" && weight != "" && height != "" && gender != "")


                val userInfo = repository.getUser(auth.currentUser?.uid!!)
                User.fromDocument(userInfo).let { user ->
                    user.name = name
                    user.age = age
                    user.height = height
                    user.weight = weight
                    user.gender = gender
                    user.profile_completed = isComplete

                    repository.updateUser(user)
                }
            } catch (e: Exception) {
                _error.value = e.localizedMessage
            }
        }
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
    }

    fun signOut() {
        authentication.signOut()
    }
}