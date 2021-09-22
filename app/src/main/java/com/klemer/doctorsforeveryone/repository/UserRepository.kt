package com.klemer.doctorsforeveryone.repository

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.klemer.doctorsforeveryone.model.User
import kotlinx.coroutines.tasks.await

class UserRepository {

    private val USERS_COLLECTION = "users"
    private val database = Firebase.firestore


    suspend fun getUser(userId: String): DocumentSnapshot {
        return database.collection(USERS_COLLECTION).document(userId).get().await()
    }

    suspend fun updateUser(user: User) {
        database.collection(USERS_COLLECTION).document(user.id).set(user).await()
    }
}