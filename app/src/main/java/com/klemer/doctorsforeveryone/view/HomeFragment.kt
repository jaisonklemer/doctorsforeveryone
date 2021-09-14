package com.klemer.doctorsforeveryone.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.google.android.material.snackbar.Snackbar
import com.klemer.doctorsforeveryone.R
import com.klemer.doctorsforeveryone.adapter.CategoryAdapter
import com.klemer.doctorsforeveryone.adapter.DoctorAdapter
import com.klemer.doctorsforeveryone.databinding.HomeFragmentBinding
import com.klemer.doctorsforeveryone.model.Category
import com.klemer.doctorsforeveryone.model.Doctor
import com.klemer.doctorsforeveryone.view_model.CategoryViewModel
import com.klemer.doctorsforeveryone.view_model.DoctorViewModel
import com.klemer.doctorsforeveryone.view_model.HomeViewModel

class HomeFragment : Fragment(R.layout.home_fragment) {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var viewModel: HomeViewModel
    private lateinit var viewModelCategory: CategoryViewModel
    private lateinit var viewModelDoctor: DoctorViewModel
    private lateinit var binding: HomeFragmentBinding
    private lateinit var recyclerViewCategory: RecyclerView
    private lateinit var recyclerViewDoctor: RecyclerView
    private var adapterCategory = CategoryAdapter {
        println("Nome da categoria: ${it.name}")
        viewModelDoctor.fetchDoctorByCategory(it.name)
    }
    private var adapterDoctor = DoctorAdapter {
        println("Id do Doctor: ${it.id}")
    }

    private val observerCategoryGetAll = Observer<List<Category>> {
        adapterCategory.refresh(it)
    }

    private val observerDoctorGetALL = Observer<List<Doctor>> {
        if (it != null) {
            adapterDoctor.refresh(it)
        } else {
            adapterDoctor.clear()
            Snackbar.make(requireView(), "Nao foi encontrado Nenhum Doctor!", Snackbar.LENGTH_LONG)
                .show()
        }

    }

    override fun onStart() {
        super.onStart()
        viewModelCategory.fetchCategory()
        viewModelDoctor.fetchDoctor()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadComponents(view)
        setupOservers()
        executeComponents()
    }

    private fun loadComponents(view: View) {
        binding = HomeFragmentBinding.bind(view)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        viewModelCategory = ViewModelProvider(this).get(CategoryViewModel::class.java)
        viewModelDoctor = ViewModelProvider(this).get(DoctorViewModel::class.java)
        recyclerViewCategory = binding.recyclerViewSpecialtyList
        recyclerViewDoctor = binding.recyclerViewDoctorsList
    }

    private fun setupOservers() {
        viewModelCategory.categoryGet.observe(viewLifecycleOwner, observerCategoryGetAll)
        viewModelDoctor.doctorGet.observe(viewLifecycleOwner, observerDoctorGetALL)
    }

    private fun executeComponents() {
        recyclerViewCategory.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        recyclerViewCategory.adapter = adapterCategory

        recyclerViewDoctor.layoutManager =
            LinearLayoutManager(requireContext())
        recyclerViewDoctor.adapter = adapterDoctor

    }

}