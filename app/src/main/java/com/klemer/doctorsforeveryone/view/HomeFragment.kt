package com.klemer.doctorsforeveryone.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.google.android.material.snackbar.Snackbar
import com.klemer.doctorsforeveryone.R
import com.klemer.doctorsforeveryone.adapter.*
import com.klemer.doctorsforeveryone.databinding.HomeFragmentBinding
import com.klemer.doctorsforeveryone.databinding.HomeHeaderBinding
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
    private lateinit var recyclerViewDoctor: RecyclerView

    private var adapterCategory = CategoryAdapter {
        println("Nome da categoria: ${it.name}")
        viewModelDoctor.fetchDoctorByCategory(it.name)
    }
    private var adapterDoctor = DoctorAdapter {
        showBottomSheetDialog(it)
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

    private fun loadComponents(view2: View) {
        binding = HomeFragmentBinding.bind(view2)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        viewModelCategory = ViewModelProvider(this).get(CategoryViewModel::class.java)
        viewModelDoctor = ViewModelProvider(this).get(DoctorViewModel::class.java)
        recyclerViewDoctor = binding.recyclerViewDoctorsList


    }

    private fun setupOservers() {
        viewModelCategory.categoryGet.observe(viewLifecycleOwner, observerCategoryGetAll)
        viewModelDoctor.doctorGet.observe(viewLifecycleOwner, observerDoctorGetALL)
    }

    private fun executeComponents() {
        //Lista de categorias
        binding.include.recyclerViewCategory.adapter = adapterCategory
        binding.include.recyclerViewCategory.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        //Lista de médicos
        recyclerViewDoctor.layoutManager = LinearLayoutManager(requireContext())
        recyclerViewDoctor.adapter = adapterDoctor

        binding.headerFragment.includeSearch.searchDoctors.addTextChangedListener(object :
            TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                println(p0)
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })

    }

    private fun showBottomSheetDialog(doctor: Doctor) {
        val bottomSheet = BottomSheetFragment.newInstance()
        val arguments = Bundle()
        arguments.putSerializable("doctor", doctor)
        bottomSheet.arguments = arguments
        bottomSheet.show(parentFragmentManager, "dialog_doctors")
    }
}