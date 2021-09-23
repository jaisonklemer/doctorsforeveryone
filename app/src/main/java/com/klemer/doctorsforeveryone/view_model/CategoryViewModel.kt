package com.klemer.doctorsforeveryone.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.klemer.doctorsforeveryone.model.Category
import com.klemer.doctorsforeveryone.repository.CategoryRepository
import kotlinx.coroutines.launch

class CategoryViewModel : ViewModel() {

    private val repository = CategoryRepository()

    var categoryInsertDelete = MutableLiveData<Boolean>()
    var categoryGet = MutableLiveData<List<Category>>()
    var category = MutableLiveData<Category>()
    var error = MutableLiveData<String>()

    fun insertCategory(category: Category) {
        viewModelScope.launch {
            try {
                repository.insertCategory(category)
                categoryInsertDelete.value = true
            } catch (e: Exception) {
                categoryInsertDelete.value = false
                error.value = e.localizedMessage
            }
        }
    }

    fun updateCategory(categoryUpdate: Category) {
        viewModelScope.launch {
            try {
                repository.updateCategory(categoryUpdate)
            } catch (e: Exception) {
                error.value = e.localizedMessage
            }
        }
    }

    fun deleteCategory(uid: String) {
        viewModelScope.launch {
            try {
                repository.deleteCategory(uid)
                categoryInsertDelete.value = true
            } catch (e: Exception) {
                error.value = e.localizedMessage
                categoryInsertDelete.value = false
            }
        }
    }

    fun fetchCategory() {
        viewModelScope.launch {
            try {
                val listOfCategory = repository.getAllCategory()
                mutableListOf<Category>().let {
                    listOfCategory.forEach { document ->
                        it.add(Category.fromDocument(document))
                    }

                    categoryGet.value = it
                }
            } catch (e: Exception) {
                error.value = e.localizedMessage
            }
        }
    }

    fun fetchCategoryById(categoryId: String) {
        viewModelScope.launch {
            try {
                val categorySnapshot = repository.getCategoryById(categoryId)
                category.value = Category.fromDocument(categorySnapshot)
            } catch (e: Exception) {
                error.value = e.localizedMessage
            }
        }
    }

}