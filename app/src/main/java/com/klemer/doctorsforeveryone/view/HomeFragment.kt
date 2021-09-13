package com.klemer.doctorsforeveryone.view

import android.content.Context
import android.os.Binder
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.klemer.doctorsforeveryone.R
import com.klemer.doctorsforeveryone.adapter.CategoryAdapter
import com.klemer.doctorsforeveryone.databinding.HomeFragmentBinding
import com.klemer.doctorsforeveryone.databinding.ItensCardsSpecialtyBinding
import com.klemer.doctorsforeveryone.model.Category
import com.klemer.doctorsforeveryone.view_model.CategoryViewModel
import com.klemer.doctorsforeveryone.view_model.HomeViewModel

class HomeFragment : Fragment(R.layout.home_fragment) {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var viewModel: HomeViewModel
    private lateinit var viewModelCategory: CategoryViewModel
    private lateinit var binding: HomeFragmentBinding
    //    private lateinit var bindingCategor: ItensCardsSpecialtyBinding
    private lateinit var recyclerView: RecyclerView
    private var adapter = CategoryAdapter {
        println("Id do usuario: ${it.id}")
    }

    private val observerCategoryGetAll = Observer<List<Category>> {
        adapter.refresh(it)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadComponents(view)
        setupOservers()
        executeComponents()
    }

    private fun loadComponents(view: View) {
        binding = HomeFragmentBinding.bind(view)
//        bindingCategor = ItensCardsSpecialtyBinding.bind(view)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        viewModelCategory = ViewModelProvider(this).get(CategoryViewModel::class.java)
        recyclerView = binding.recyclerViewSpecialtyList
    }

    private fun setupOservers() {
        viewModelCategory.categoryGet.observe(viewLifecycleOwner, observerCategoryGetAll)
    }

    private fun executeComponents() {
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = adapter

        viewModelCategory.fetchCategory()
    }

}