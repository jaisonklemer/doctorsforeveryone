package com.klemer.doctorsforeveryone.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.klemer.doctorsforeveryone.R
import com.klemer.doctorsforeveryone.model.Category
import com.klemer.doctorsforeveryone.view_model.CategoryViewModel

class CategoryFragment : Fragment(R.layout.category_fragment) {

    companion object {
        fun newInstance() = CategoryFragment()
    }

    private lateinit var viewModel: CategoryViewModel

    private val observerCategoryInsert = Observer<Boolean> {
        Snackbar.make(requireView(), "Category created", Snackbar.LENGTH_LONG).show()
    }

    private val categoryList = Observer<List<Category>> {
        it.forEach{ cat ->
            println(cat)
        }
    }

    private val categoryById = Observer<Category> {
        println(it)
    }

    private val observerError = Observer<String> {
        Snackbar.make(requireView(), it, Snackbar.LENGTH_LONG).show()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(CategoryViewModel::class.java)

        setupObservers()
        executeTest()

    }

    private fun setupObservers() {
        viewModel.categoryInsertDelete.observe(viewLifecycleOwner, observerCategoryInsert)
        viewModel.error.observe(viewLifecycleOwner, observerError)
        viewModel.categoryGet.observe(viewLifecycleOwner, categoryList)
        viewModel.category.observe(viewLifecycleOwner, categoryById)

    }

    private fun executeTest() {
        // CRUD Category all done
//        viewModel.insertCategory(Category(id = null, name = "Genocida"))
//        viewModel.updateCategory(Category(id = "atDwnZtuNdQgACSi2jOd", name = "Pediatra"))
//        viewModel.deleteCategory("")
//        viewModel.fetchCategory()
//        viewModel.fetchCategoryById("IXhRMsR6bVkNWIFZnFFl")


    }


}