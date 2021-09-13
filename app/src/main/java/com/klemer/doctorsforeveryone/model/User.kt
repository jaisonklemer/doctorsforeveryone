package com.klemer.doctorsforeveryone.model

import com.google.firebase.firestore.DocumentSnapshot

data class User(
    var id: String,
    val admin: Boolean
) {
    companion object {
        fun fromDocument(document: DocumentSnapshot): User {
            return User(
                id = document.id,
                admin = document["admin"] as Boolean
            )
        }
    }
}
