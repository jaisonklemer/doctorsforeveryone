package com.klemer.doctorsforeveryone.model

import com.google.firebase.firestore.DocumentSnapshot


data class Category(
    val id: String?,
    val name: String,
    val image: String
) {
    companion object {
        fun fromDocument(document: DocumentSnapshot): Category {
            return Category(
                id = document.id,
                name = document["name"] as String,
                image = document["image"] as String
            )
        }
    }
}
