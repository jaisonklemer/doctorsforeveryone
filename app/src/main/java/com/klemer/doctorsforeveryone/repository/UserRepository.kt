package com.klemer.doctorsforeveryone.repository

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.klemer.doctorsforeveryone.model.User

class UserRepository {

    private val USERS_COLLECTION = "users"
    private val database = Firebase.firestore


    fun getUser(userId: String, callback: (User?) -> Unit) {
        val task = database.collection(USERS_COLLECTION).document(userId).get()

        task.addOnSuccessListener {
            if (it != null) {
                callback(User.fromDocument(it))
            }
        }

        task.addOnFailureListener {
            callback(null)
            println(it.localizedMessage)
        }
    }
    fun updateUser(user: User, callback: (User?) -> Unit){
        database.collection(USERS_COLLECTION)
            .document(user.id.toString())
            .set(user)
            .addOnSuccessListener {
                println("Sucess")
            }
            .addOnFailureListener{
                println("ERRO")
            }

    }
}