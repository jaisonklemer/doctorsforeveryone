package com.klemer.doctorsforeveryone.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class AuthenticationRepository {

    private val auth = FirebaseAuth.getInstance()

    fun signInWithEmailAndPassword(
        email: String,
        password: String,
        callback: (FirebaseUser?, String?) -> Unit,
    ) {
        val task = auth.signInWithEmailAndPassword(email, password)

        task.addOnSuccessListener { authResult ->
            callback(authResult.user, null)
        }
        task.addOnFailureListener {
            callback(null, it.localizedMessage)
        }
    }

    fun signUpWithEmailAndPassword(
        email: String,
        password: String,
        callback: (FirebaseUser?, String?) -> Unit,
    ) {
        val task = auth.createUserWithEmailAndPassword(email, password)

        task.addOnSuccessListener { authResult ->
            authResult.user?.sendEmailVerification()
            callback(authResult.user, null)
        }
        task.addOnFailureListener { failureError ->
            callback(null, failureError.localizedMessage)
        }
    }

}