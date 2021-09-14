package com.klemer.doctorsforeveryone.model

import com.google.firebase.firestore.DocumentSnapshot

data class User(
    var id: String,
    val admin: Boolean,
    var name : String,
    var age : String,
    var weight :String,
    var height: String
) {
    companion object {
        fun fromDocument(document: DocumentSnapshot): User {
            return User(
                id = document.id,
                admin = document["admin"] as Boolean,
                name = document["name"] as String,
                age = document["age"] as String,
                weight = document["weight"] as String,
                height = document["height"] as String,
            )
        }
    }
}
