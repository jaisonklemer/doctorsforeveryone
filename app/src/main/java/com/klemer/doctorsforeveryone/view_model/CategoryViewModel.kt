package com.klemer.doctorsforeveryone.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.klemer.doctorsforeveryone.model.Category
import com.klemer.doctorsforeveryone.repository.CategoryRepository

class CategoryViewModel : ViewModel() {

    private val repository = CategoryRepository()

    var categoryInsertDelete = MutableLiveData<Boolean>()
    var categoryGet = MutableLiveData<List<Category>>()
    var category = MutableLiveData<Category>()
    var error = MutableLiveData<String>()

    fun insertCategory(category: Category) {
        repository.insertCategory(category) { insert, e ->
            categoryInsertDelete.value = insert
            error.value = e
        }
    }

    fun updateCategory(categoryUpdate: Category) {
        repository.updateCategory(categoryUpdate) { cat, e ->
            category.value = cat
            error.value = e
        }
    }

    fun deleteCategory(uid: String) {
        repository.deleteCategory(uid) { boolean ->
            categoryInsertDelete.value = boolean
        }
    }

    fun fetchCategory() {
        repository.getAllCategory { list, e ->
            categoryGet.value = list
            error.value = e
        }
    }

    fun fetchCategoryById(categoryId: String) {
        repository.getCategoryById(categoryId) { cat, e ->
            category.value = cat
            error.value = e
        }
    }

}