package com.klemer.doctorsforeveryone.repository

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.klemer.doctorsforeveryone.model.Category

class CategoryRepository {

    private val CATEGORY_COLLECTION = "categories"
    private val database = Firebase.firestore

    fun insertCategory(category: Category, callback: (Boolean, String?) -> Unit) {
        database.collection(CATEGORY_COLLECTION).add(category).apply {
            this.addOnFailureListener {
                callback(false, it.message)
            }
            this.addOnSuccessListener {
                callback(true, null)
            }
        }
    }

    fun updateCategory(category: Category, callback: (Category?, String?) -> Unit) {
        database.collection(CATEGORY_COLLECTION)
            .document(category.id.toString())
            .set(category)
            .addOnSuccessListener {
                Log.d(TAG,"Document Updated")
            }
            .addOnFailureListener { error ->

                Log.w(TAG, "Failure updated", error)
            }
    }

    fun deleteCategory(uid: String, callback: (Boolean) -> Unit) {
        database.collection(CATEGORY_COLLECTION)
            .document(uid)
            .delete()
            .addOnSuccessListener {
                callback(true)
            }
            .addOnFailureListener {
                callback(false)
            }
    }

    fun getAllCategory(callback: (List<Category>?, String?) -> Unit) {
        database.collection(CATEGORY_COLLECTION).get().apply {
            this.addOnFailureListener {
                callback(null, it.message)
            }
            this.addOnSuccessListener { listOfDocument ->
                mutableListOf<Category>().let { categoryList ->
                    listOfDocument.forEach { document ->
                        categoryList.add(Category.fromDocument(document))
                    }
                    callback(categoryList, null)
                }
            }
        }
    }

    fun getCategoryById(categoryId: String, callback: (Category?, String?) -> Unit) {
        database.collection(CATEGORY_COLLECTION).document(categoryId).get().apply {
            this.addOnFailureListener {
                callback(null, it.message)
            }
            this.addOnSuccessListener {
                callback(Category.fromDocument(it), null)
            }
        }
    }

}