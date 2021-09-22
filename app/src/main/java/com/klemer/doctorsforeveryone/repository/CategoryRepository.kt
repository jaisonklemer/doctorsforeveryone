package com.klemer.doctorsforeveryone.repository

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.klemer.doctorsforeveryone.model.Category
import kotlinx.coroutines.tasks.await

class CategoryRepository {

    private val CATEGORY_COLLECTION = "categories"
    private val database = Firebase.firestore

    suspend fun insertCategory(category: Category): DocumentReference {
        return database.collection(CATEGORY_COLLECTION).add(category).await()
    }

    suspend fun updateCategory(category: Category) {
        database.collection(CATEGORY_COLLECTION).document(category.id.toString()).set(category)
            .await()
    }

    suspend fun deleteCategory(uidCategory: String) {
        database.collection(CATEGORY_COLLECTION)
            .document(uidCategory)
            .delete().await()
    }

    suspend fun getAllCategory(): QuerySnapshot {
        return database.collection(CATEGORY_COLLECTION).get().await()
    }

    suspend fun getCategoryById(categoryId: String): DocumentSnapshot {
        return database.collection(CATEGORY_COLLECTION).document(categoryId).get().await()
    }
}